package rs.travel.bookingWithEase.dto;

import java.util.List;

public class QuickFlightReservationDTO {

	private Integer discount;
	private List<Long> seats;

	public QuickFlightReservationDTO() {
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public List<Long> getSeats() {
		return seats;
	}

	public void setSeats(List<Long> seats) {
		this.seats = seats;
	}

}
