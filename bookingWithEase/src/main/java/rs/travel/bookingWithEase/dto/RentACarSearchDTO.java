package rs.travel.bookingWithEase.dto;

import java.sql.Date;

public class RentACarSearchDTO {

	private String name;
	private String address;
	private Date pickUp;
	private Date dropOff;

	public RentACarSearchDTO() {
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public Date getPickUp() {
		return pickUp;
	}

	public Date getDropOff() {
		return dropOff;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPickUp(Date pickUp) {
		this.pickUp = pickUp;
	}

	public void setDropOff(Date dropOff) {
		this.dropOff = dropOff;
	}

}
