package rs.travel.bookingWithEase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class VehicleRate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private VehicleReservation reservation;
	
	private Double rate;
	
	public VehicleRate() {
	}

	public Long getId() {
		return id;
	}

	public Double getRate() {
		return rate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@JsonIgnore
	public VehicleReservation getReservation() {
		return reservation;
	}

	public void setReservation(VehicleReservation reservation) {
		this.reservation = reservation;
	}
	
}
