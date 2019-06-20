package rs.travel.bookingWithEase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.model.FlightRate;

public interface IFlightRateRepository extends JpaRepository<FlightRate, Long>{

	@Query("SELECT avg(rate) from FlightRate fr where fr.reservation.flight.airline.id = ?1")
	Double findAverageByCompany(Long id);
	
	@Query("SELECT avg(rate) from FlightRate fr where fr.reservation.flight.id = ?1")
	Double findAverageByFlight(Long id);

	List<FlightRate> findByReservationFlight(Flight flight);
}
