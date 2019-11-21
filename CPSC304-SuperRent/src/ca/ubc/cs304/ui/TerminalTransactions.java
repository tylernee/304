package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.VehicleTypeModel;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;
	
	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;

	public TerminalTransactions() {
	}

	/**
	 * Displays simple text interface
	 */ 
	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Make reservation ");
			System.out.println("2. View Available Vehicles");
			System.out.println("3. Do rental");
			System.out.println("4. Show branch");
			System.out.println("5. Quit");
			System.out.print("Please choose one of the above 5 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
//					handleInsertOption();
					handleReservation();
					break;
				case 2:  
//					handleDeleteOption();
					viewAvailableVehicles();
					break;
				case 3: 
//					handleUpdateOption();
					rentOutVehicle();
					break;
				case 4:  
					delegate.showCustomers();
					break;
				case 5:
					handleQuitOption();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
					break;
				}
			}
		}		
	}

	// TODO customer transactions CHANGE RETURN TYPE AND ADD PARAMETERS WHEN NECESSARY
	private void handleReservation() {
		// Reservation (confNo, vtname, cellphone, fromDate, fromTime, toDate, toTime)

	}
	// TODO
	private void viewAvailableVehicles() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To view number of available vehicles, please select a specific car type:");
        String cartype = scanner.next();
        System.out.println("Next, enter a location");
        String location = scanner.next();
        System.out.println("Lastly, enter a time interval:");
        String timeinterval = scanner.next();
        VehicleTypeModel[] vehicleTypes = delegate.showVehicleTypes();
        System.out.println(vehicleTypes);

	}

	//TODO clerk transactions CHANGE RETURN TYPE AND ADD PARAMETERS WHEN NECESSARY
	private void rentOutVehicle() {
		//	Rent(rid, vid, cellphone, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, ExpDate, confNo)

		// 2 cases: if a reservation has been made, grab reservation tuple and grab necessary info
		//			if no reservation, in which we create a new tuple for customer (if neccesary)
		System.out.println("Please enter your reservation number, enter 0 if you do not have one:");
		int resNo = readInteger(false);
		if (resNo != 0) {
			// proceed rental
			// Reservation (confNo, vtname, cellphone, fromDate, fromTime, toDate, toTime)
			System.out.println("do rental");
			delegate.handleRent(resNo);
			// remove tuple with confNo from confirmation and create one to rentm

		} else {
			System.out.println("create new customer model, insert into db, make them pick a vehicle, etc");
		}


	}

	// TODO
	private void returnVehicle() {

	}
	// TODO, this may have to be split up into 4 methods looking at the description
	private void generateReport() {

	}

 	/*
 	*****
 	* ****
 		ALL FUNCTIONS BELOW ARE FROM THE SAMPLE JAVA PROGRAM, USE THEM FOR REFERENCE AND TEST THOROUGLY
 	*****
 	*****
 	***/

	private void handleDeleteOption() {
		int branchId = INVALID_INPUT;
		while (branchId == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to delete: ");
			branchId = readInteger(false);
			if (branchId != INVALID_INPUT) {
				delegate.deleteBranch(branchId);
			}
		}
	}


	private void handleInsertOption() {
		int id = INVALID_INPUT;
		while (id == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to insert: ");
			id = readInteger(false);
		}

		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the branch name you wish to insert: ");
			name = readLine().trim();
		}

		// branch address is allowed to be null so we don't need to repeatedly ask for the address
		System.out.print("Please enter the branch address you wish to insert: ");
		String address = readLine().trim();
		if (address.length() == 0) {
			address = null;
		}

		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the branch city you wish to insert: ");
			city = readLine().trim();
		}

		int phoneNumber = INVALID_INPUT;
		while (phoneNumber == INVALID_INPUT) {
			System.out.print("Please enter the branch phone number you wish to insert: ");
			phoneNumber = readInteger(true);
		}

		BranchModel model = new BranchModel(address,
											city,
											id,
											name,
											phoneNumber);
		delegate.insertBranch(model);
	}
	
	private void handleQuitOption() {
		System.out.println("Good Bye!");
		
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}
		
		delegate.terminalTransactionsFinished();
	}
	
	private void handleUpdateOption() {
		int id = INVALID_INPUT;
		while (id == INVALID_INPUT) {
			System.out.print("Please enter the branch ID you wish to update: ");
			id = readInteger(false);
		}
		
		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the branch name you wish to update: ");
			name = readLine().trim();
		}

		delegate.updateBranch(id, name);
	}
	
	private int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}
	
	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}
}
