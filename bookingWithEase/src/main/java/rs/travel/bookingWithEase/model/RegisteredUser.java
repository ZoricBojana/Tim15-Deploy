package rs.travel.bookingWithEase.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RegisteredUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RoomReservation> roomReservations = new HashSet<>();

	@OneToMany(mappedBy = "vehicle_user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<VehicleReservation> vehicleReservations = new HashSet<>();

	@OneToMany(mappedBy = "fUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<FlightReservation> flightReservations = new HashSet<>();

	public RegisteredUser() {
		super();
	}

	public RegisteredUser(Long id, String username, String firstName, String lastName, String email, String password,
			String city, String telephoneNumber, String passportNumber) {
		super(id, username, firstName, lastName, email, password, city, telephoneNumber, passportNumber);

	}

	public Set<RoomReservation> getRoomReservations() {
		return roomReservations;
	}

	public Set<VehicleReservation> getVehicleReservations() {
		return vehicleReservations;
	}

	public void setRoomReservations(Set<RoomReservation> roomReservations) {
		this.roomReservations = roomReservations;
	}

	public void setVehicleReservations(Set<VehicleReservation> vehicleReservations) {
		this.vehicleReservations = vehicleReservations;
	}

	@JsonIgnore
	public Set<FlightReservation> getFlightReservations() {
		return flightReservations;
	}

	@JsonIgnore
	public void setFlightReservations(Set<FlightReservation> flightReservations) {
		this.flightReservations = flightReservations;
	}

}
