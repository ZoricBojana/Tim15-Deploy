package rs.travel.bookingWithEase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.FlightInvite;
import rs.travel.bookingWithEase.repository.IFlightInviteRepository;

@Service
public class FlightInviteService {

	@Autowired
	private IFlightInviteRepository flightInviteRepository;

	public FlightInvite findByFriendEmailAndReservationId(String email, Long flightId) {
		return flightInviteRepository.findByFriendEmailAndReservationId(email, flightId);
	}

	public void save(FlightInvite invite) {
		flightInviteRepository.save(invite);
	}

	public List<FlightInvite> findByReservationIdAndSeatIdIsNot(Long flightReservationId, Long seatId) {
		return flightInviteRepository.findByReservationIdAndSeatIdIsNot(flightReservationId, seatId);
	}

	public List<FlightInvite> findByReservationIdAndAcceptedAndSeatId(Long flightReservationId, boolean accepted,
			Long seatId) {
		return flightInviteRepository.findByReservationIdAndAcceptedAndSeatId(flightReservationId, accepted, seatId);
	}

	public List<FlightInvite> findByReservationIdAndAcceptedAndSeatIdIsNot(Long flightReservationId, boolean accepted,
			Long seatId) {
		return flightInviteRepository.findByReservationIdAndAcceptedAndSeatIdIsNot(flightReservationId, accepted, seatId);
	}
	
	public FlightInvite findByFriendEmailAndReservationIdAndSeatIdIsNot(String email, Long flightReservationId,
			Long seatId) {
		return flightInviteRepository.findByFriendEmailAndReservationIdAndSeatIdIsNot(email, flightReservationId,
				seatId);
	}

	public FlightInvite findByReservationId(Long quickFliResId) {
		return flightInviteRepository.findByReservationId(quickFliResId);
	}

}
