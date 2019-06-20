package rs.travel.bookingWithEase.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.QuickFlightReservation;

public interface QuickFlightReservationRepository extends JpaRepository<QuickFlightReservation, Long> {

	Collection<QuickFlightReservation> findByFlightId(Long id);

	Collection<QuickFlightReservation> findByFlightIdAndFUserIsNull(Long id);

}
