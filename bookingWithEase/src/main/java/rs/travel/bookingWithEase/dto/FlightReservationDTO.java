package rs.travel.bookingWithEase.dto;

public class FlightReservationDTO {

	private Long flightReservationId;
	private Long userId;
	private Long seatId;

	private String firstname;
	private String lastname;
	private String passport;

	public FlightReservationDTO() {
	}

	public Long getFlightReservationId() {
		return flightReservationId;
	}

	public void setFlightReservationId(Long flightReservationId) {
		this.flightReservationId = flightReservationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

}
