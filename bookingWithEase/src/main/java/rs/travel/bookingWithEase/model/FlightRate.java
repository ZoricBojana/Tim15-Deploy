package rs.travel.bookingWithEase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class FlightRate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private FlightReservation reservation;
	
	private Double rate;
	
	public FlightRate() {
	}

	public Long getId() {
		return id;
	}

	public FlightReservation getReservation() {
		return reservation;
	}

	public Double getRate() {
		return rate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setReservation(FlightReservation reservation) {
		this.reservation = reservation;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
}
