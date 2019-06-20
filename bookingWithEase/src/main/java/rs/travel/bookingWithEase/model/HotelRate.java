package rs.travel.bookingWithEase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class HotelRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private RoomReservation reservation;
	
	private Double rate;
	
	public HotelRate() {
	}

	public Long getId() {
		return id;
	}

	@JsonIgnore
	public RoomReservation getReservation() {
		return reservation;
	}

	public Double getRate() {
		return rate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setReservation(RoomReservation reservation) {
		this.reservation = reservation;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
}
