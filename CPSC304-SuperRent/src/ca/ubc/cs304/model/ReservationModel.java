package ca.ubc.cs304.model;

/**
 * The intent for this class is to update/store information about a single branch
 */
public class ReservationModel {
	// Reservation (confNo, vtname, cellphone, fromDate, fromTime, toDate, toTime)
	private final int confNo;
	private final String vtname;
	private final String cellphone;
	private final String fromDate;
	private final String fromTime;
	private final String toDate;
	private final String toTime;


	public ReservationModel(int confNo, String vtname, String cellphone, String fromDate, String fromTime, String toDate, String toTime) {
		this.confNo = confNo;
		this.vtname = vtname;
		this.cellphone = cellphone;
		this.fromDate = fromDate;
		this.fromTime = fromTime;
		this.toDate = toDate;
		this.toTime = toTime;
	}

	public int getConfNo() {
		return confNo;
	}
	public String getVtName() {
		return vtname;
	}
	public String getCellphone() {
		return cellphone;
	}
	public String getFromDate() {
		return fromDate;
	}
	public String getFromTime() {
		return fromTime;
	}
	public String getToDate() {
		return toDate;
	}
	public String getToTime() {
		return toTime;
	}
}
