package ca.ubc.cs304.model;

/**
 * The intent for this class is to update/store information about a single branch
 */
public class CustomerModel {
//	Customer (cellphone, name, address, dlicense)
	private final String cellphone;
	private final String name;
	private final String address;
	private final int dlicense;
	public CustomerModel(String cellphone, String name, String address, int dlicense) {
		this.cellphone = cellphone;
		this.name = name;
		this.address = address;
		this.dlicense = dlicense;
	}

	public String getCellphone() { return cellphone; }

	public String getName() {return name; }

	public String getAddress() {return address;}

	public int getDlicense() { return dlicense; }


}
