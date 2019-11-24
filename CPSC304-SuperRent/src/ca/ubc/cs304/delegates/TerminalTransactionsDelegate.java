package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.ReservationModel;
import ca.ubc.cs304.model.VehicleModel;
import ca.ubc.cs304.model.VehicleTypeModel;

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
	public VehicleTypeModel[] showVehicleTypes();
	public VehicleModel[] showVehicles();
	public ReservationModel[] showReservations();
	public void updateBranch(int branchId, String name);


	public void makeReservation(int dlicense);

	
	public void terminalTransactionsFinished();
}
