package rs.travel.bookingWithEase.dto;

import java.util.List;

public class InviteFriendsFlightDTO {

	private Long flightReservationId;	
	private List<Long> invFriends;

	public InviteFriendsFlightDTO() {
		super();
	}
	
	public Long getFlightReservationId() {
		return flightReservationId;
	}

	public void setFlightReservationId(Long flightReservationId) {
		this.flightReservationId = flightReservationId;
	}

	public List<Long> getInvFriends() {
		return invFriends;
	}

	public void setInvFriends(List<Long> invFriends) {
		this.invFriends = invFriends;
	}
	
	
	
}
