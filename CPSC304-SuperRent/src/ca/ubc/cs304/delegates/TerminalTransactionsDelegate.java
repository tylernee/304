package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.CustomerModel;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {
	public void deleteBranch(int branchId);
	public void insertBranch(BranchModel model);
	public void showCustomers();
	public void updateBranch(int branchId, String name);


	public void makeReservation(int dlicense);
	public String handleRent(int confNo, String cardName, int cardNo, String expDate);
	public String handleRentNoReservation(String vtname, String cardName, int cardNo, String expDate);
	public void insertNewCustomer(CustomerModel customer);
	public void returnVehicle(int rid, String date, String time, int odometer, boolean fulltank);
	
	public void terminalTransactionsFinished();
}
