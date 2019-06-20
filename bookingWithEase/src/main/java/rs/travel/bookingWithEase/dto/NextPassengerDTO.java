package rs.travel.bookingWithEase.dto;

import java.util.List;

public class NextPassengerDTO {

	private Long inviterId;
	private Long flightReservationId;
	private List<Long> selectedSeats;
	
	public NextPassengerDTO() {
		super();
	}

	public Long getFlightReservationId() {
		return flightReservationId;
	}

	public void setFlightReservationId(Long flightReservationId) {
		this.flightReservationId = flightReservationId;
	}

	public List<Long> getSelectedSeats() {
		return selectedSeats;
	}

	public void setSelectedSeats(List<Long> selectedSeats) {
		this.selectedSeats = selectedSeats;
	}

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

}
