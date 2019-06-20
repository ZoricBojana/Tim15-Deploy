package rs.travel.bookingWithEase.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.travel.bookingWithEase.model.VehicleReservation;


public interface IVehicleReservationRepository extends JpaRepository<VehicleReservation, Long>{

	@Query("SELECT vr FROM VehicleReservation vr WHERE vr.vehicle_user.id = ?1 order by check_in_date desc")
	List<VehicleReservation> findByUser(Long userId);
	
	@Query("SELECT sum(totalPrice) FROM VehicleReservation vr WHERE vr.vehicle.branch.rac.id = ?1 AND vr.checkOutDate >= ?2 AND vr.checkOutDate <= ?3")
	Double findIncome(Long racId, Date start, Date end);

}
