package rs.travel.bookingWithEase.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.travel.bookingWithEase.model.VehicleRate;

public interface IVehicleRateRepository extends JpaRepository<VehicleRate, Long>{

	@Query("SELECT avg(rate) from VehicleRate vr where vr.reservation.vehicle.branch.rac.id = ?1")
	Double findAverageByCompany(Long id);
	
	@Query("SELECT avg(rate) from VehicleRate vr where vr.reservation.vehicle.id = ?1")
	Double findAverageByVehicle(Long id);
	
	@Query("SELECT avg(rate) from VehicleRate vr where vr.reservation.vehicle.branch.rac.id = ?1 AND vr.reservation.checkOutDate >= ?2 AND vr.reservation.checkOutDate <= ?3")
	Double findAverageByCompanyPeriod(Long id, Date start, Date end);
	
	@Query("SELECT avg(rate) from VehicleRate vr where vr.reservation.vehicle.id = ?1 AND vr.reservation.checkOutDate >= ?2 AND vr.reservation.checkOutDate <= ?3")
	Double findAverageByVehiclePeriod(Long id, Date start, Date end);
	
}
