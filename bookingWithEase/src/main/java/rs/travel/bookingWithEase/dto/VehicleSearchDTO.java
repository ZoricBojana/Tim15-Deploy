package rs.travel.bookingWithEase.dto;

import java.util.Date;

public class VehicleSearchDTO {
	private Long rentacarId;
	private String type;
	private String gear;
	private Double minPrice;
	private Double maxPrice;
	private Date pickUp;
	private Date dropOff;

	public VehicleSearchDTO() {
	}

	public String getType() {
		return type;
	}

	public String getGear() {
		return gear;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public Date getPickUp() {
		return pickUp;
	}

	public Date getDropOff() {
		return dropOff;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setPickUp(Date pickUp) {
		this.pickUp = pickUp;
	}

	public void setDropOff(Date dropOff) {
		this.dropOff = dropOff;
	}

	public Long getRentacarId() {
		return rentacarId;
	}

	public void setRentacarId(Long rentacarId) {
		this.rentacarId = rentacarId;
	}
}
