package rs.travel.bookingWithEase.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.model.FlightReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;

public interface IFlightReservationRepository extends JpaRepository<FlightReservation, Long>{

	FlightReservation findByFUserAndFlightId(RegisteredUser user, Long flightId);

	@Query("SELECT fr FROM FlightReservation fr WHERE fr.fUser.id = ?1 order by checkInDate desc")
	List<FlightReservation> findByUser(Long userId);

	List<FlightReservation> findByFlight(Flight f);

	List<FlightReservation> findByFlightAndCheckInDateBetween(Flight f, Date start, Date end);
}
