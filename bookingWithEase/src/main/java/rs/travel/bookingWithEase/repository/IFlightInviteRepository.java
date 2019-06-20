package rs.travel.bookingWithEase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.FlightInvite;

public interface IFlightInviteRepository extends JpaRepository<FlightInvite, Long>{

	FlightInvite findByFriendEmailAndReservationId(String email, Long flightId);
	
	List<FlightInvite> findByReservationIdAndSeatIdIsNot(Long flightReservationId, Long seatId);

	List<FlightInvite> findByReservationIdAndAcceptedAndSeatId(Long flightReservationId, boolean accepted, Long seatId);

	FlightInvite findByFriendEmailAndReservationIdAndSeatIdIsNot(String email, Long flightReservationId, Long seatId);

	List<FlightInvite> findByReservationIdAndAcceptedAndSeatIdIsNot(Long flightReservationId, boolean accepted, Long seatId);

	FlightInvite findByReservationId(Long quickFliResId);

}
