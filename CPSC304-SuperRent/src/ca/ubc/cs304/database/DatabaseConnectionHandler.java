package ca.ubc.cs304.database;

import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.CustomerModel;
import ca.ubc.cs304.model.RentModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

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
	private int creditCardNo;
	private String expDate;
	private String customerName;
	private String address;
	private String cellphone;
	private String dlicense;
	private String fromDate;
	private String vtname;

	public String getVtname() {
		return vtname;
	}

	public void setVtname(String vtname) {
		this.vtname = vtname;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	private String toDate;
	private String fromTime;
	private String toTime;

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

	public int getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(int creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
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
				// TODO change the getString for dlicense
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

	public String handleRentNoReservation(String vtname, String cardName, int cardNo, String expDate, String dlicense,
										  String fromDate, String toDate, String fromTime, String toTime) {
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
			RentModel rental = new RentModel(rid, vid, dlicense, fromDate, fromTime, toDate, toTime, odometer,
					cardName, cardNo, expDate, -1);
			insertNewRental(rental);
			System.out.println("rental no reservation complete! Heres your rental ID: " + rid);


		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
		return "";
	}
	public String handleRent(int confNo, String cardName, int cardNo, String expDate) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reservations WHERE confNo = " + confNo);
			ResultSetMetaData rsmd = rs.getMetaData();

			// GET NECCESARRY INFO FROM  RESERVATIONS
			String dlicense ="", fromDate="", fromTime="", toDate="", toTime = "", vtname="";
			int vid=-1, odometer=-1;
			while(rs.next()) {
				dlicense = rs.getString("dlicense");
				fromDate = rs.getString("fromDate");
				fromTime = rs.getString("fromTime");
				toDate = rs.getString("toDate");
				toTime = rs.getString("toTime");
				vtname = rs.getString("vtname");
			}
			Statement stmt2 = connection.createStatement();
			// GET VID AND ODOMETER FROM ANY VEHICLE OF THE RESERVED TYPE
			ResultSet rs2 = stmt2.executeQuery("SELECT vid, odometer FROM Vehicles WHERE vtname = '" + vtname + "' AND status = 'for_rent'");
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
			int rid = randomInt.nextInt(60000)/2 + randomInt.nextInt(300)/2;

			//create a new RentModel and push to DB
			RentModel rental = new RentModel(rid, vid, dlicense, fromDate, fromTime, toDate, toTime, odometer,
					cardName, cardNo, expDate, confNo);
			System.out.println(toDate);
			insertNewRental(rental);

			System.out.println("rental complete! Heres your rental ID: " + rid);


		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

		return "";
	}

	private void insertNewRental(RentModel rental) {
		//	Rent(rid, vid, dlicense, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, ExpDate, confNo)
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Rentals VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			ps.setInt(1, rental.getRid());
			ps.setInt(2, rental.getVid());
			ps.setString(3, rental.getDLicense());
			ps.setString(4, rental.getFromDate());
			ps.setString(5, rental.getFromTime());
			ps.setString(6, rental.getToDate());
			ps.setString(7, rental.getToTime());
			ps.setInt(8, rental.getOdometer());
			ps.setString(9, rental.getCardName());
			ps.setInt(10, rental.getCardNo());
			ps.setString(11, rental.getExpDate());
			if (confNo == -1) {
				System.out.println("setting null");
				ps.setNull(12, java.sql.Types.INTEGER);
			} else {
				ps.setInt(12, rental.getConfNo());
			}
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

	public void returnVehicle(int rid, String date, String time, int odometer, boolean fulltank) {
		try {
			Statement stmt = connection.createStatement();
			// TODO: ADD THE DATES TO CALCULATE THE TOTAL
			ResultSet rs = stmt.executeQuery("SELECT vid, confNo, odometer FROM Rentals WHERE rid = " + rid);
			rs.next();
			int vid = rs.getInt("vid");
			System.out.println(vid);

			ResultSet rs2 = stmt.executeQuery("SELECT status FROM Vehicles WHERE vid = " + vid);
			rs2.next();
			String status = rs2.getString("status");
			if (!status.equals("rented")) {
				System.out.println("Vehicle was never rented!");
				rollbackConnection();
				return;
			}
			System.out.println("returned");
			// change status in Vehicle  to for_rent , insert new value in returns calculate costs and return a receipt (GUI)

		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
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

	public void deleteBranch(int branchId) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE branch_id = ?");
			ps.setInt(1, branchId);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	public void insertBranch(BranchModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");
			ps.setInt(1, model.getId());
			ps.setString(2, model.getName());
			ps.setString(3, model.getAddress());
			ps.setString(4, model.getCity());
			if (model.getPhoneNumber() == 0) {
				ps.setNull(5, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, model.getPhoneNumber());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public BranchModel[] getBranchInfo() {
		ArrayList<BranchModel> result = new ArrayList<BranchModel>();

		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM vehicles");

			 // get info on ResultSet
			 ResultSetMetaData rsmd = rs.getMetaData();
			//
			 System.out.println(rs.getString("status"));


			 // display column names;
			 for (int i = 0; i < rsmd.getColumnCount(); i++) {
			 // get column name and print it
			 System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			 }

			while (rs.next()) {
				System.out.println(rs.getString("snum"));
//				BranchModel model = new BranchModel(rs.getString("branch_addr"), rs.getString("branch_city"),
//						rs.getInt("branch_id"), rs.getString("branch_name"), rs.getInt("branch_phone"));
//				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new BranchModel[result.size()]);
	}

	public void updateBranch(int id, String name) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");
			ps.setString(1, name);
			ps.setInt(2, id);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
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
		creditCardNo = -1;
		expDate = "";
		customerName ="";
		address = "";
		cellphone = "";
		dlicense = "";
		fromDate = "";
		vtname = "";
	}
}
