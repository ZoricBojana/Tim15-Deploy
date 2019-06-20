package rs.travel.bookingWithEase.dto;

import java.util.Date;
import java.util.List;

public class QuickVehicleReservationDTO {

	private Date checkIn;
	private Date checkOut;
	private Integer discount;
	private List<Long> specialOffers;
	private List<Long> vehicles;

	public QuickVehicleReservationDTO() {
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public Integer getDiscount() {
		return discount;
	}

	public List<Long> getSpecialOffers() {
		return specialOffers;
	}

	public List<Long> getVehicles() {
		return vehicles;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public void setSpecialOffers(List<Long> specialOffers) {
		this.specialOffers = specialOffers;
	}

	public void setVehicles(List<Long> vehicles) {
		this.vehicles = vehicles;
	}
}
