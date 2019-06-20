package rs.travel.bookingWithEase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.RentACar;

public interface IRACRepository extends JpaRepository<RentACar, Long> {
	
	//@Query("SELECT rac FROM RentACar WHERE rac.name LIKE CONCAT('%', ?1,'%') AND rac.address LIKE CONCAT('%', ?2,'%') ")
	List<RentACar> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(String name, String address);
}
