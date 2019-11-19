package ca.ubc.cs304.model;

/**
 * The intent for this class is to update/store information about a single branch
 */
public class RentModel {
	private final int rid;
	private final int vid;
	private final String cellphone;
	private final String fromDate;
	private final String fromTime;
	private final String toDate;
	private final String toTime;
	private final int odometer;
	private final String cardName;
	private final int cardNo;
	private final String expDate;
	private final int confNo;
//	Rent(rid, vid, cellphone, fromDate, fromTime, toDate, toTime, odometer, cardName, cardNo, ExpDate, confNo)

	public RentModel(int rid, int vid, String cellphone, String fromDate, String fromTime, String toDate, String toTime,
					 int odometer, String cardName, int cardNo, String expDate, int confNo) {
		this.rid = rid;
		this.vid = vid;
		this.cellphone = cellphone;
		this.fromDate = fromDate;
		this.fromTime = fromTime;
		this.toDate = toDate;
		this.toTime = toTime;
		this.odometer = odometer;
		this.cardName = cardName;
		this.cardNo = cardNo;
		this.expDate = expDate;
		this.confNo = confNo;

	}
	public int getRid() {
		return rid;
	}

	public int getVid() {
		return vid;
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

	public int getOdometer() {
		return odometer;
	}

	public String getCardName() {
		return cardName;
	}

	public int getCardNo() {
		return cardNo;
	}

	public String getExpDate() {
		return expDate;
	}

	public int getConfNo() {
		return confNo;
	}



}
