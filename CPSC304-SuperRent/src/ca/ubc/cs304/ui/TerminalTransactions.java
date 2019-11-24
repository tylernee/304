package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.ReservationModel;
import ca.ubc.cs304.model.VehicleModel;
import ca.ubc.cs304.model.VehicleTypeModel;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("To make a reservation, please enter a vehicle type:");
        System.out.println("Any, Economy, Compact, Mid-size, Standard, Full-size, SUV, Truck");
        String cartype = scanner.next();
        System.out.println("Next, enter a location");
        System.out.println("Type NA if none");
        String location = scanner.next();
        System.out.println("Next, enter a time interval for pickup and return:");
        String timeinterval = scanner.next();
        if (checkVehicleAvailable(cartype,location,timeinterval)){
            System.out.println("The requested vehicle is available. Next, please enter your name:");
            String cname = scanner.next();
            System.out.println("Next, enter your cellphone number:");
            String cnumber = scanner.next();
        } else {
            System.out.println("The requested vehicle is not available. Please try again!");
        }




        // check if customer exists, if not add to database.

        // return a confirmation no.
	}
	// TODO
	private void viewAvailableVehicles() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To view number of available vehicles, please select a specific car type:");
        System.out.println("Any, Economy, Compact, Mid-size, Standard, Full-size, SUV, Truck");
        String cartype = scanner.next();
        System.out.println("Next, enter a location:");
        System.out.println("Type NA if none");
        String location = scanner.next();
        System.out.println("Lastly, enter a time interval:");
        String timeinterval = scanner.next();
        checkVehicleAvailable(cartype,location,timeinterval);
	}

	public boolean checkVehicleAvailable(String vehicletype, String location, String timeinterval){
        VehicleModel[] vehicles = delegate.showVehicles();
        int vehicleCount = 0;
        VehicleModel[] availableVehicles = new VehicleModel[vehicles.length];
        for(int i=0; i < vehicles.length; i++){
            if(vehicles[i].getStatus().contains("for_rent") && (vehicles[i].getVtName().contains(vehicletype) || vehicletype.contains("Any"))
                    && (vehicles[i].getLocation().contains(location) || location.contains("NA"))){
                availableVehicles[vehicleCount] = vehicles[i];
                vehicleCount++;
            }
        }
        if (vehicleCount == 0){
            System.out.println("No Vehicles Available! Please select another.");
            return false;
        } else {
            System.out.println("Number of Available Vehicles: " + vehicleCount);
            Scanner scanner = new Scanner((System.in));
            System.out.println("Do you want to view more details about the vehicles? Y or N");
            String response = scanner.next();
            if (response.contains("Y")){
                for (int i = 0; i < availableVehicles.length; i++) {
                    if(availableVehicles[i] != null){
                        System.out.println("Vehicle " + (i + 1) + " - Type: " + availableVehicles[i].getVtName()
                                + ", Make: " + availableVehicles[i].getMake() + ", Model: " +availableVehicles[i].getModel()
                                + ", Year: " + availableVehicles[i].getYear() + ", Odometer: " +availableVehicles[i].getOdometer()
                                + ", Color: " +availableVehicles[i].getColor() + ", Location: " +availableVehicles[i].getLocation());
                    }
                }
            }
            return true;
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
