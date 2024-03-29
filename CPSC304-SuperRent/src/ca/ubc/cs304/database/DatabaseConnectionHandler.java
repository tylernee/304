package ca.ubc.cs304.database;

import ca.ubc.cs304.model.CustomerModel;
import ca.ubc.cs304.model.RentModel;
import ca.ubc.cs304.model.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	// private static final String ORACLE_URL =
	// "jjdbc:oracle:thin:@localhost:1522:stu";

	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;
	private boolean isReserved = false;
	private int confNo;
	private String creditCardNo;
	private Date expDate;
	private String creditCardName;
	private String customerName;
	private String address;
	private String cellphone;
	private String dlicense;
	private Date fromDate;
	private String vtname;
	private String branchLocation;

	public String getBranchLocation() {
		return branchLocation;
	}
	public void setBranchLocation(String branchLocation) {
		this.branchLocation = branchLocation;
	}
	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}


	public String getVtname() {
		return vtname;
	}

	public void setVtname(String vtname) {
		this.vtname = vtname;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Time getFromTime() {
		return fromTime;
	}

	public void setFromTime(Time fromTime) {
		this.fromTime = fromTime;
	}

	public Time getToTime() {
		return toTime;
	}

	public void setToTime(Time toTime) {
		this.toTime = toTime;
	}

	private Date toDate;
	private Time fromTime;
	private Time toTime;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getDlicense() {
		return dlicense;
	}

	public void setDlicense(String dlicense) {
		this.dlicense = dlicense;
	}

	public int getConfNo() {
		return confNo;
	}

	public void setConfNo(int confNo) {
		this.confNo = confNo;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public void setIsReservation(boolean res) {
		this.isReserved = res;
	}

	public boolean isReservation() {
		return this.isReserved;
	}

	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public CustomerModel[] getCustomers() {
		ArrayList<CustomerModel> customers = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMERS");
			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();
			// display column names;
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			}
			while (rs.next()) {
//				Customer (cellphone, name, address, dlicense)
				CustomerModel customer = new CustomerModel(rs.getString("cellphone"),
						rs.getString("name"), rs.getString("address"), rs.getString("dlicense"));
				customers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers.toArray(new CustomerModel[customers.size()]);
	}

	public void insertNewCustomer(CustomerModel customer) {
		//	Customer (cellphone, name, address, dlicense)
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO CUSTOMERS VALUES (?,?,?,?)");
			ps.setString(1, customer.getCellphone());
			ps.setString(2, customer.getName());
			ps.setString(3, customer.getAddress());
			ps.setString(4, customer.getDlicense());

//			not sure if needed for non-primary keys ps.setNull(4, java.sql.Types.INTEGER);
			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public String handleRentNoReservation(String vtname, String cardName, String cardNo, Date expDate, String dlicense,
										  Date fromDate, Date toDate, Time fromTime, Time toTime) {
		try {
			Statement stmt = connection.createStatement();
			// GET VID AND ODOMETER FROM ANY VEHICLE OF THE RESERVED TYPE
			ResultSet rs2 = stmt.executeQuery("SELECT vid, odometer FROM Vehicles WHERE vtname = '" + vtname + "' AND status = 'for_rent'");
			int vid = -1, odometer = -1;
			while (rs2.next()) {
				vid = rs2.getInt("VID");
				odometer = rs2.getInt("odometer");
			}
			PreparedStatement ps = connection.prepareStatement("UPDATE Vehicles SET status = ? WHERE vid = ?");
			ps.setString(1, "rented");
			ps.setInt(2, vid);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Vehicle " + vid + " does not exist!");
			}
			connection.commit();

			Random randomInt = new Random();
			int rid = randomInt.nextInt(60000)/2 + randomInt.nextInt(300)/2;
			// TODO make a new reservation table and then make a rent model so that dumb null error disappears
			int confNo = (int) (Math.random()*10000) + 1;
			System.out.println(vid);
			insertNewReservation(new ReservationModel(confNo, vid, vtname, dlicense, fromDate, fromTime, toDate, toTime));

			RentModel rental = new RentModel(rid, vid, dlicense, fromDate, fromTime, toDate, toTime, odometer,
					cardName, cardNo, expDate, confNo);
			insertNewRental(rental);
			System.out.println("rental no reservation complete! Heres your rental ID: " + rid);


		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return "";
	}
	public void handleRent(int confNo, String cardName, String cardNo, Date expDate) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reservations WHERE confNo = " + confNo);
			ResultSetMetaData rsmd = rs.getMetaData();

			// GET NECCESARRY INFO FROM  RESERVATIONS
			String dlicense = "", vtname ="";
			Date fromDate = null;
			Time fromTime = null;
			Date toDate = null;
			Time toTime = null;
			int vid = -1, odometer = -1;
			while (rs.next()) {
				dlicense = rs.getString("dlicense");
				fromDate = rs.getDate("fromDate");
				fromTime = rs.getTime("fromTime");
				toDate = rs.getDate("toDate");
				toTime = rs.getTime("toTime");
				vtname = rs.getString("vtname");
			}
			Statement stmt2 = connection.createStatement();
			// GET VID AND ODOMETER FROM ANY VEHICLE OF THE RESERVED TYPE
			ResultSet rs2 = stmt2.executeQuery("SELECT vid, odometer FROM Vehicles WHERE vtname = '" + vtname + "' AND status = 'reserved'");
			rsmd = rs2.getMetaData();
			while (rs2.next()) {
				vid = rs2.getInt("VID");
				odometer = rs2.getInt("odometer");
			}
			// get first vehicle, set vehicles status to RENTED,
			PreparedStatement ps = connection.prepareStatement("UPDATE Vehicles SET status = ? WHERE vid = ?");
			ps.setString(1, "rented");
			ps.setInt(2, vid);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Vehicle " + vid + " does not exist!");
			}
			connection.commit();

			Random randomInt = new Random();
			int rid = randomInt.nextInt(60000) / 2 + randomInt.nextInt(300) / 2;

			//create a new RentModel and push to DB
			RentModel rental = new RentModel(rid, vid, dlicense, fromDate, fromTime, toDate, toTime, odometer,
					cardName, cardNo, expDate, confNo);
			System.out.println(toDate);
			insertNewRental(rental);

			System.out.println("rental complete! Heres your rental ID: " + rid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertNewRental(RentModel rental) {
		System.out.println();
		//	Rent(rid, vid, dlicense, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, ExpDate, confNo)
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Rentals VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			ps.setInt(1, rental.getRid());
			ps.setInt(2, rental.getVid());
			ps.setString(3, rental.getDLicense());
			ps.setString(4, rental.getFromDate().toString());
			ps.setString(5, rental.getFromTime().toString());
			ps.setString(6, rental.getToDate().toString());
			ps.setString(7, rental.getToTime().toString());
			ps.setInt(8, rental.getOdometer());
			ps.setString(9, rental.getCardName());
			ps.setString(10, rental.getCardNo());
			ps.setString(11, rental.getExpDate().toString());
//
			ps.setInt(12, rental.getConfNo());

//			not sure if needed for non-primary keys ps.setNull(4, java.sql.Types.INTEGER);
			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			System.out.println("cause it was null?");
			rollbackConnection();
		}
	}

	private int calculateCost(String vtname, String rentDate, String returnDate, int origOdometer, int curOdometer) {
		// calc weekly rate and daily rate :: get values from VEHICLETYPE table
		// calc krate from origOdometer - curOdometer
		try {
			Statement stmt = connection.createStatement();
			System.out.println(vtname);
			ResultSet rs = stmt.executeQuery("SELECT * FROM VehicleTypes WHERE vtname = '" + vtname + "'");
			rs.next();
			int weeklyRate = rs.getInt("wrate");
			int hourlyRate = rs.getInt("hrate");
			int dailyRate = rs.getInt("drate");
			int weeklyIRate = rs.getInt("wirate");
			int dailyIRate = rs.getInt("dirate");
			int hourlyIRate = rs.getInt("hirate");
			int kilometerRate = rs.getInt("krate");

			// format the dates and get difference in days
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			java.util.Date firstDate = sdf.parse(rentDate);
			java.util.Date secondDate = sdf.parse(returnDate);

			long totalDaysInMillis =  Math.abs(secondDate.getTime() - firstDate.getTime());
			int diff = (int) TimeUnit.DAYS.convert(totalDaysInMillis, TimeUnit.MILLISECONDS);

			int totalWeeks = (int) Math.floor(diff/7);
			int remainingDays = diff%7;

			int total = (origOdometer + curOdometer) * kilometerRate;
			if (totalWeeks > 0) {
				total+= weeklyRate*totalWeeks;
				total+= weeklyIRate*totalWeeks;
			}
			total+=dailyIRate*remainingDays;
			total+=dailyRate*remainingDays;
			return total;

		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public int returnVehicle(int rid, String date, int odometer, boolean fulltank) {
		try {
			Statement stmt = connection.createStatement();
			// TODO: ADD THE DATES TO CALCULATE THE TOTAL
			ResultSet rs = stmt.executeQuery("SELECT vid, confNo, odometer, fromDate FROM Rentals WHERE rid = " + rid);
			rs.next();
			int vid = rs.getInt("vid");
			int originalOdometer = rs.getInt("odometer");
			String fromDate = rs.getString("fromDate");
			System.out.println(vid);

			ResultSet rs2 = stmt.executeQuery("SELECT vtname, status FROM Vehicles WHERE vid = " + vid);
			rs2.next();
			String vtname = rs2.getString("vtname");
			String status = rs2.getString("status");
			if (!status.equals("rented")) {
				System.out.println("Vehicle was never rented!");
				rollbackConnection();
				return -1;
			}
			System.out.println(originalOdometer);
			System.out.println(odometer);
			int totalKmUsed = originalOdometer - odometer;

			PreparedStatement ps = connection.prepareStatement("UPDATE vehicles SET status = ?, odometer = ? WHERE vid = ?");
			ps.setString(1, "for_rent");
			ps.setInt(2, totalKmUsed);
			ps.setInt(3, vid);
			ps.executeUpdate();
			connection.commit();

			//todays date
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
			java.util.Date curDate = new java.util.Date();
			System.out.println("curdate: " + sdf.format(curDate));
			int cost = calculateCost(vtname, date, sdf.format(curDate), originalOdometer, odometer);
//			int cost = 1;


			PreparedStatement ps2 = connection.prepareStatement("INSERT INTO Returns VALUES (?, ?, ?, ?, ?)");
			ps2.setInt(1, rid);
			ps2.setString(2, date);
			ps2.setInt(3, odometer);
			ps2.setInt(4, (fulltank) ? 1 : 0);
			ps2.setInt(5, cost);
			ps2.executeUpdate();
			System.out.println("after execute");

			connection.commit();
			System.out.println("rental complete costs: " + cost);
			ps.close();
			ps2.close();
			return cost;
			// change status in Vehicle  to for_rent , insert new value in returns calculate costs and return a receipt (GUI)
			// update odometer, get price from the difference.




		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
			return -1;
		}
	}






	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}


    public String generateReportForBranch(String location) {
        String report = "";
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal = Calendar.getInstance();
		String todaysDate = df.format(cal.getTime());
		System.out.println(todaysDate);
        try {
            Statement stmt = connection.createStatement();
            String createView = "create view test (location, vtname, totalRents) as " +
                    "select v.location, v.vtname, count(v.vtname) " +
                    "from vehicles v where v.vid in (select r.vid from rentals r where r.fromDate = '23-NOV-19') " +
                    "group by v.vtname, v.location HAVING v.location= '" + location + "'";
            stmt.executeUpdate(createView);
            Statement stmt2 = connection.createStatement();
            ResultSet rs = stmt2.executeQuery("SELECT * from test");
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                // get column name and print it
                report += String.format("%-15s", rsmd.getColumnName(i + 1));
            }
            report += "\n";
            while (rs.next()) {
                report += String.format("%-15s", rs.getString(1));
                report += String.format("%-15s", rs.getString(2));
                report += String.format("%-15s", rs.getString(3));
                report += "\n";
            }
            report += "\n";

            rs = stmt2.executeQuery("Select SUM(totalRents) from test");
            rs.next();
            report += String.format("Total daily rents of SuperRent at branch (" + location + "): " + rs.getInt(1));

            stmt.executeUpdate("DROP VIEW test");
            return report;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }

    public String generateReportForAll() {
        String report = "";
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal = Calendar.getInstance();
		String todaysDate = df.format(cal.getTime());
		System.out.println(todaysDate);
        try {
            Statement stmt = connection.createStatement();
            String createView = "CREATE VIEW test2 (location, vtname, totalRents) as " +
                    "select v.location, v.vtname, COUNT(v.vtname) " +
                    "from vehicles v where v.vid in (select r.vid from rentals r where r.fromDate = '" + todaysDate + "') " +
                    "group by v.vtname, v.location";
//
            stmt.executeUpdate(createView);

            Statement stmt2 = connection.createStatement();
            ResultSet rs = stmt2.executeQuery("SELECT * from test2");
            ResultSetMetaData rsmd = rs.getMetaData();
            // display column names;
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                // get column name and print it
                report += String.format("%-15s", rsmd.getColumnName(i + 1));
            }
            report += "\n";
            while (rs.next()) {
                report += String.format("%-15s", rs.getString(1));
                report += String.format("%-15s", rs.getString(2));
                report += String.format("%-15s", rs.getString(3));
                report += "\n";


            }
            report += "\n";

            rs = stmt2.executeQuery("Select SUM(totalRents) from test2");
            rs.next();
            report += String.format("Total daily rents of SuperRent: " + rs.getInt(1));
//            System.out.println(report);

//
            stmt.executeUpdate("DROP VIEW test2");
            return report;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }

    public String generateReportReturnsForBranch(String branch) {
		String report = "";
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal = Calendar.getInstance();
		String todaysDate = df.format(cal.getTime());
//		String todaysDate = "2000-7-29";
		System.out.println(todaysDate);
		try {
			Statement stmt = connection.createStatement();
			String createView = "CREATE VIEW test2 (location, vtname, totalReturns, revenue) as " +
					"select v.location, v.vtname, COUNT(v.vtname), SUM(r.rvalue) " +
					"from vehicles v, returns r where r.rid in (select r.rid from rentals r1 where r1.toDate = '" + todaysDate + "') " +
					"group by v.vtname, v.location HAVING v.location =  '" + branch + "'";
//
			stmt.executeUpdate(createView);

			Statement stmt2 = connection.createStatement();
			ResultSet rs = stmt2.executeQuery("SELECT * from test2");
			ResultSetMetaData rsmd = rs.getMetaData();
			// display column names;
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				// get column name and print it
				report += String.format("%-15s", rsmd.getColumnName(i + 1));
			}
			report += "\n";
			while (rs.next()) {
				report += String.format("%-15s", rs.getString(1));
				report += String.format("%-15s", rs.getString(2));
				report += String.format("%-15s", rs.getString(3));
				report += "\n";


			}
			report += "\n";

			rs = stmt2.executeQuery("Select SUM(revenue) from test2");
			rs.next();
			report += String.format("Total sales of from this branch: " + rs.getInt(1));
			System.out.println(report);

//
			stmt.executeUpdate("DROP VIEW test2");
			return report;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

    public String generateReportReturnsAll() {
		String report = "";
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
		Calendar cal = Calendar.getInstance();
		String todaysDate = df.format(cal.getTime());
//		String todaysDate = "2000-7-29";
		System.out.println(todaysDate);
		try {
			Statement stmt = connection.createStatement();
			String createView = "CREATE VIEW test2 (location, vtname, totalReturns, revenue) as " +
					"select v.location, v.vtname, COUNT(v.vtname), SUM(r.rvalue) " +
					"from vehicles v, returns r where r.rid in (select r.rid from rentals r1 where r1.toDate = '" + todaysDate + "') " +
					"group by v.vtname, v.location";
//
			stmt.executeUpdate(createView);

			Statement stmt2 = connection.createStatement();
			ResultSet rs = stmt2.executeQuery("SELECT * from test2");
			ResultSetMetaData rsmd = rs.getMetaData();
			// display column names;
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				// get column name and print it
				report += String.format("%-15s", rsmd.getColumnName(i + 1));
			}
			report += "\n";
			while (rs.next()) {
				report += String.format("%-15s", rs.getString(1));
				report += String.format("%-15s", rs.getString(2));
				report += String.format("%-15s", rs.getString(3));
				report += "\n";


			}
			report += "\n";

			rs = stmt2.executeQuery("Select SUM(revenue) from test2");
			rs.next();
			report += String.format("Total sales of SuperRent: " + rs.getInt(1));
            System.out.println(report);

//
			stmt.executeUpdate("DROP VIEW test2");
			return report;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}


	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void reset() {
		isReserved = false;
		confNo = -1;
		creditCardNo = "";
		expDate = null;
		customerName ="";
		address = "";
		cellphone = "";
		dlicense = "";
		fromDate = null;
		vtname = "";
		creditCardName = "";
	}
    public RentModel[] getRentals() {
        ArrayList<RentModel> rentals = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RENTALS");
            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();
            // display column names;
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                // get column name and print it
                System.out.printf("%-15s", rsmd.getColumnName(i + 1));
            }
            System.out.println("");
            while (rs.next()) {

                RentModel vehicle = new RentModel(
                        Integer.parseInt(rs.getString("rid")),
                        Integer.parseInt(rs.getString("vid")),
                        rs.getString("dlicense"),
                        Date.valueOf(rs.getString("fromDate")),
                        Time.valueOf(rs.getString("fromTime")),
                        Date.valueOf(rs.getString("toDate")),
                        Time.valueOf(rs.getString("toTime")),
                        Integer.parseInt(rs.getString("odometer")),
                        rs.getString("cardName"),
                        rs.getString("cardNo"),
                        Date.valueOf(rs.getString("expDate")),
                        Integer.parseInt(rs.getString("confNo")));
                rentals.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals.toArray(new RentModel[rentals.size()]);
    }

    public void updateVehicles(int vid, String status) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE VEHICLES SET status = ? WHERE vid = ?");
            ps.setString(1, status);
            ps.setInt(2, vid);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Vehicle " + vid + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

	public VehicleModel[] getVehicles() {
		ArrayList<VehicleModel> vehicles = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLES");
			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();
			// display column names;
//			for (int i = 0; i < rsmd.getColumnCount(); i++) {
//				// get column name and print it
//				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
//			}
			while (rs.next()) {

				VehicleModel vehicle = new VehicleModel(
						Integer.parseInt(rs.getString("vid")),
						rs.getString("vlicense"),
						rs.getString("make"),
						rs.getString("model"),
						Integer.parseInt(rs.getString("year")),
						rs.getString("color"),
						Integer.parseInt(rs.getString("odometer")),
						rs.getString("status"),
						rs.getString("vtName"),
						rs.getString("location"),
						rs.getString("city"));
				vehicles.add(vehicle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vehicles.toArray(new VehicleModel[vehicles.size()]);
	}

	public VehicleTypeModel[] getVehicleTypes() {
		ArrayList<VehicleTypeModel> vehicleTypes = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLETYPES");
			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();
			// display column names;
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			}
			System.out.println("");
			while (rs.next()) {

				VehicleTypeModel vehicleType = new VehicleTypeModel(
						rs.getString("vtname"),
						rs.getString("features"),
						Integer.parseInt(rs.getString("wrate")),
						Integer.parseInt(rs.getString("drate")),
						Integer.parseInt(rs.getString("hrate")),
						Integer.parseInt(rs.getString("wirate")),
						Integer.parseInt(rs.getString("dirate")),
						Integer.parseInt(rs.getString("hirate")),
						Integer.parseInt(rs.getString("krate")));
				vehicleTypes.add(vehicleType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vehicleTypes.toArray(new VehicleTypeModel[vehicleTypes.size()]);
	}

	public void insertNewReservation(ReservationModel reservation) {
		//	Customer (confNo integer, vtname varchar, dlicense varchar,fromDate varchar,fromTime varchar, toDate varchar,toTime varchar)
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO RESERVATIONS VALUES (?,?,?,?,?,?,?,?)");
            DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
            DateFormat tf = new SimpleDateFormat("HH:MM:SS");
			ps.setInt(1, reservation.getConfNo());
			ps.setInt(2, reservation.getVid());
			ps.setString(3, reservation.getVtName());
			ps.setString(4, reservation.getDlicense());
			ps.setString(5, reservation.getFromDate().toString());
            ps.setString(6, reservation.getFromTime().toString());
            ps.setString(7, reservation.getToDate().toString());
            ps.setString(8, reservation.getToTime().toString());

//			not sure if needed for non-primary keys ps.setNull(4, java.sql.Types.INTEGER);
			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	public ReservationModel[] getReservations() {
		ArrayList<ReservationModel> reservations = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM RESERVATIONS");
			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();
			// display column names;
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			}
			System.out.println("");
			while (rs.next()) {

				ReservationModel reservation = new ReservationModel(
						Integer.parseInt(rs.getString("confNo")),
                        Integer.parseInt(rs.getString("vid")),
						rs.getString("vtname"),
						rs.getString("dlicense"),
                        Date.valueOf(rs.getString("fromDate")),
						Time.valueOf(rs.getString("fromTime")),
						Date.valueOf(rs.getString("toDate")),
						Time.valueOf(rs.getString("toTime")));
				reservations.add(reservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservations.toArray(new ReservationModel[reservations.size()]);
	}


}
