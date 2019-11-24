package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.guiWindow;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class SuperRent implements LoginWindowDelegate, TerminalTransactionsDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;

	public SuperRent() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void start() {
		//loginWindow = new LoginWindow();
		//loginWindow.showFrame(this);
//		Scanner sc = new Scanner(System.in);
//		System.out.println("enter username (should be ora_CWLID: ");
//		System.out.println("make sure you set up SSH Tunneling with the server");
//		String username = sc.next();
//		System.out.println("enter password (should be aSTUDENTNUMBER: ");
//		String pass = sc.next();
		String username = "ora_atsf";
		String pass = "a26411348";
		login(username, pass);
	}
	
	/**
	 * LoginWindowDelegate Implementation
	 * 
     * connects to Oracle database with supplied username and password
     */ 
	public void login(String username, String password) {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			//loginWindow.dispose();

			guiWindow guiWindow = new guiWindow(dbHandler);
			guiWindow.makeWindow();

//			TerminalTransactions transaction = new TerminalTransactions();
//			transaction.showMainMenu(this);
		}
	}
	
	/**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Insert a branch with the given info
	 */
    public void insertBranch(BranchModel model) {
    	dbHandler.insertBranch(model);
    }

    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Delete branch with given branch ID.
	 */ 
    public void deleteBranch(int branchId) {
    	dbHandler.deleteBranch(branchId);
    }
    
    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Update the branch name for a specific ID
	 */

    public void updateBranch(int branchId, String name) {
    	dbHandler.updateBranch(branchId, name);
    }

    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Displays information about varies bank branches.
	 */
    public void showCustomers() {
    	CustomerModel[] customers = dbHandler.getCustomers();
    }

    public VehicleTypeModel[] showVehicleTypes() {
		VehicleTypeModel[] vehicleTypes = dbHandler.getVehicleTypes();
		return vehicleTypes;
	}

	public VehicleModel[] showVehicles() {
		VehicleModel[] vehicles = dbHandler.getVehicles();
		return vehicles;
	}

	public ReservationModel[] showReservations() {
    	ReservationModel[] reservations = dbHandler.getReservations();
    	return reservations;
	}

	
    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that it is done with what it's 
     * doing so we are cleaning up the connection since it's no longer needed.
     */ 
    public void terminalTransactionsFinished() {
    	dbHandler.close();
    	dbHandler = null;
    	
    	System.exit(0);
    }


    public void makeReservation(int dlicense) {
//    	dbHandler.makeReservation(dlicense);
	}


    
	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		SuperRent superRent = new SuperRent();
		superRent.start();
	}
}
