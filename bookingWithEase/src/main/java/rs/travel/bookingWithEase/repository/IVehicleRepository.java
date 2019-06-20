package rs.travel.bookingWithEase.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.travel.bookingWithEase.model.Vehicle;

public interface IVehicleRepository extends JpaRepository<Vehicle, Long>{
	
	Vehicle findByRegistrationNumber(String registrationNumber);
	
	ArrayList<Vehicle> findByTypeContainingIgnoreCaseAndGearContainingIgnoreCase(String type, String gear);
	
	@Query("SELECT v FROM Vehicle v WHERE v.branch.rac.id = ?1 AND ?3 >= v.pricePerDay AND ?2 <= v.pricePerDay")
	ArrayList<Vehicle> findByPriceRange(Long rentacarId, double minPrice, double maxPrice);
	
	@Query("SELECT DISTINCT v FROM Vehicle v " +
		    "WHERE v.branch.rac.id = ?1 AND v.id NOT IN " +
					"(SELECT v2.id FROM Vehicle v2, VehicleReservation vr " +
		             "WHERE v2.id = vr.vehicle.id " +
					 "AND ((vr.checkInDate <= ?2 AND vr.checkOutDate >= ?2) " +
		             "OR  (vr.checkInDate < ?3 AND vr.checkOutDate >= ?3) " +
					 "OR  (?2 <= vr.checkInDate AND ?3 >= vr.checkInDate)))")
	ArrayList<Vehicle> findByAvailability(Long rentacarId, Date checkIn, Date checkOut);
}
