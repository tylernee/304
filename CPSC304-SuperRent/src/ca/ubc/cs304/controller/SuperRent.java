package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.guiWindow;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class SuperRent implements LoginWindowDelegate {
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
		String username = "ora_moya33";
		String pass = "a35798347";
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
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		SuperRent superRent = new SuperRent();
		superRent.start();
	}
}
