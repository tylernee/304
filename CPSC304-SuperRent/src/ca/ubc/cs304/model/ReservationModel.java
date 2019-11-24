package ca.ubc.cs304.model;

import java.sql.Time;
import java.sql.Date;

/**
 * The intent for this class is to update/store information about a single branch
 */
public class ReservationModel {
	// Reservation (confNo, vtname, cellphone, fromDate, fromTime, toDate, toTime)
	private final int confNo;
	private final int vid;
	private final String vtname;
	private final String dlicense;
	private final Date fromDate;
	private final Time fromTime;
	private final Date toDate;
	private final Time toTime;


	public ReservationModel(int confNo,int vid, String vtname, String dlicense, Date fromDate, Time fromTime, Date toDate, Time toTime) {
		this.confNo = confNo;
		this.vid = vid;
		this.vtname = vtname;
		this.dlicense = dlicense;
		this.fromDate = fromDate;
		this.fromTime = fromTime;
		this.toDate = toDate;
		this.toTime = toTime;
	}

	public int getConfNo() {
		return confNo;
	}
	public int getVid() { return vid;}
	public String getVtName() {
		return vtname;
	}
	public String getDlicense() {
		return dlicense;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public Time getFromTime() { return fromTime; }
	public Date getToDate() {
		return toDate;
	}
	public Time getToTime() {
		return toTime;
	}
}
