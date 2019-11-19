package ca.ubc.cs304.model;

/**
 * The intent for this class is to update/store information about a single branch
 */
public class ReturnModel {
	private final int rid;
	private final String date;
	private final int odometer;
	private final boolean fulltank;
	private final int value;

	public ReturnModel(int rid, String date, int odometer, boolean fulltank, int value) {
		this.rid = rid;
		this.date = date;
		this.odometer = odometer;
		this.fulltank = fulltank;
		this.value = value;
	}

	public int getRid() {
		return rid;
	}

	public String getDate() {
		return date;
	}

	public int getOdometer() {
		return odometer;
	}

	public boolean isFulltank() {
		return fulltank;
	}

	public int getValue() {
		return value;
	}


}
