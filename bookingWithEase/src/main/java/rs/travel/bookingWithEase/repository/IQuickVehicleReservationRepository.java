package rs.travel.bookingWithEase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.travel.bookingWithEase.model.QuickVehicleReservation;

public interface IQuickVehicleReservationRepository extends JpaRepository<QuickVehicleReservation, Long> {

	@Query("SELECT qvr from QuickVehicleReservation qvr WHERE qvr.vehicle.branch.rac.id = ?1")
	List<QuickVehicleReservation> findByRentacar(Long racId);
}
