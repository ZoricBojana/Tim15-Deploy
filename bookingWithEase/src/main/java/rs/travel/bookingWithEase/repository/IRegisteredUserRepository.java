package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.FlightReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;

public interface IRegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {

	RegisteredUser findByFlightReservationsId(Long flightReservationId);

	RegisteredUser findByFlightReservations(FlightReservation fRes);

}
