package rs.travel.bookingWithEase.dto;

import java.util.Date;

public class VehicleReservationDTO {
	private Long id;

	private Long user_id;

	private Date checkInDate;
	private Date checkOutDate;
	private Double totalPrice;
	
	public VehicleReservationDTO() {
	}

	public Long getId() {
		return id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
