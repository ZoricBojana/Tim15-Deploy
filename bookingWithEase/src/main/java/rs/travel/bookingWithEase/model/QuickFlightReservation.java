package rs.travel.bookingWithEase.model;

import javax.persistence.Entity;

@Entity
public class QuickFlightReservation extends FlightReservation {

	private Integer discount;

	public QuickFlightReservation() {
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
}
