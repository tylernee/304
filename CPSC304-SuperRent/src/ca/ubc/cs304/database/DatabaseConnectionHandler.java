package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;

import java.sql.*;
import java.util.ArrayList;

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

	public String handleRent(int confNo) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Reservations WHERE confNo = " + confNo);
			ResultSetMetaData rsmd = rs.getMetaData();

			// Reservation (confNo, vtname, cellphone, fromDate, fromTime, toDate, toTime)
			// Vehicle (~vid~, vlicense, make, model, year, color, odometer, status, vtname, location, city)
			//	Rent(rid, vid, cellphone, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, ExpDate, confNo)
			String cellphone ="", fromDate="", fromTime="", toDate="", toTime = "", vtname="";
			int vid=-1, odometer=-1;
			while(rs.next()) {
				cellphone = rs.getString("cellphone");
				fromDate = rs.getString("fromDate");
				fromTime = rs.getString("fromTime");
				toDate = rs.getString("toDate");
				toTime = rs.getString("toTime");
				vtname = rs.getString("vtname");
			}
//			System.out.println("SELECT vid, odometer FROM Vehicles WHERE vtname = '" + vtname + "' AND status = 'for_rent'");
			Statement stmt2 = connection.createStatement();

			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM Vehicles");
			rsmd = rs2.getMetaData();
			System.out.println(rs2.next());
			while (rs2.next()) {
				vid = rs2.getInt("vid");
				odometer = rs2.getInt("odometer");
			}
			System.out.println(vid);
//			System.out.println(cellphone + fromDate + fromTime + toDate + toTime + odometer);



		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

		return "";
	}

	private void insertNewRental(RentModel rental) {
		//	Rent(rid, vid, cellphone, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, ExpDate, confNo)
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO CUSTOMERS VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, rental.getRid());
			ps.setInt(2, rental.getVid());
			ps.setString(3, rental.getCellphone());
			ps.setDate(4, rental.getFromDate());
			ps.setTime(5, rental.getFromTime());
			ps.setDate(6, rental.getToDate());
			ps.setInt(7, rental.getOdometer());
			ps.setString(8, rental.getCardName());
			ps.setInt(9, rental.getCardNo());
			ps.setDate(10, rental.getExpDate());
			ps.setInt(11, rental.getConfNo());
//			not sure if needed for non-primary keys ps.setNull(4, java.sql.Types.INTEGER);
			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
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
                        rs.getString("cellphone"),
                        rs.getDate("fromDate"),
                        rs.getTime("fromTime"),
                        rs.getDate("toDate"),
                        rs.getTime("toTime"),
                        Integer.parseInt(rs.getString("odometer")),
                        rs.getString("cardName"),
                        Integer.parseInt(rs.getString("cardNo")),
                        rs.getDate("expDate"),
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
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			}
			System.out.println("");
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
			ps.setInt(1, reservation.getConfNo());
			ps.setInt(2, reservation.getVid());
			ps.setString(3, reservation.getVtName());
			ps.setString(4, reservation.getDlicense());
			ps.setDate(5, reservation.getFromDate());
            ps.setTime(6, reservation.getFromTime());
            ps.setDate(7, reservation.getToDate());
            ps.setTime(8, reservation.getToTime());

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
						rs.getDate("fromDate"),
						rs.getTime("fromTime"),
						rs.getDate("toDate"),
						rs.getTime("toTime"));
				reservations.add(reservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservations.toArray(new ReservationModel[reservations.size()]);
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
}
