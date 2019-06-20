package rs.travel.bookingWithEase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.Seat;

public interface ISeatRepository extends JpaRepository<Seat, Long>{

	List<Seat> findByFlightIdOrderByIdAsc(Long flightId);

	List<Seat> findByFlightIdAndAvailable(Long flightId, Boolean available);

}
