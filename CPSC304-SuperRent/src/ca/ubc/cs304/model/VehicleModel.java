package ca.ubc.cs304.model;

/**
 */
public class VehicleModel {


	// Vehicle (vid, vlicense, make, model, year, color, odometer, status, vtname, location, city)

	private final int vid;
	private final String vlicense;
	private final String make;
	private final String model;
	private final int year;
	private final String color;
	private final int odometer;
	private final boolean status;
	private final String vtName;
	private final String location;
	private final String city;

	public VehicleModel(int vid, String vlicense, String make, String model,
						int year, String color, int odometer, boolean status, String vtName, String location, String city) {
		this.vid = vid;
		this.vlicense = vlicense;
		this.make = make;
		this.model = model;
		this.year = year;
		this.color = color;
		this.odometer = odometer;
		this.status = status;
		this.vtName = vtName;
		this.location = location;
		this.city = city;
	}

	public int getVid() {
		return vid;
	}

	public String getVlicense() {
		return vlicense;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	public String getColor() {
		return color;
	}

	public int getOdometer() {
		return odometer;
	}

	public boolean isStatus() {
		return status;
	}

	public String getVtName() {
		return vtName;
	}

	public String getLocation() {
		return location;
	}

	public String getCity() {
		return city;
	}


}
