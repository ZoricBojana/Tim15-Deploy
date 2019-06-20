package rs.travel.bookingWithEase.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.travel.bookingWithEase.model.Hotel;

public interface IHotelRepository extends JpaRepository<Hotel, Long> {
	
	@Query("SELECT h FROM Hotel h " +
		   "WHERE lower(h.name) like lower(concat('%', ?1,'%')) " + 
		   "AND lower(h.address) like lower(concat('%', ?2,'%'))")
	ArrayList<Hotel> findByNameAndAddress(String name, String address);
	
	@Query("SELECT h FROM Hotel h " +
	       "WHERE lower(h.name) like lower(concat('%', ?1,'%')) " +
	              "AND lower(h.address) like lower(concat('%', ?2,'%')) " +
	              "AND h.stars = ?3")
	ArrayList<Hotel> findByNameAddressStars(String name, String address, int stars);
	
	@Query("SELECT h FROM Hotel h " +
		       "WHERE lower(h.name) like lower(concat('%', ?1,'%')) " +
		              "AND lower(h.address) like lower(concat('%', ?2,'%')) " +
		              "AND h.rating >= ?3")
	ArrayList<Hotel> findByNameAddressRating(String name, String address, double rating);
	
	@Query("SELECT h FROM Hotel h " +
		       "WHERE lower(h.name) like lower(concat('%', ?1,'%')) " +
		              "AND lower(h.address) like lower(concat('%', ?2,'%')) " +
		              "AND h.stars = ?3 AND  h.rating >= ?4")
	ArrayList<Hotel> findByNameAddressStarsRating(String name, String address, int stars, double rating);
	
	@Query("SELECT DISTINCT h2 FROM Hotel h2, Room r " +
		    "WHERE h2.id = r.hotel.id AND r.id NOT IN " +
					"(SELECT r2.id FROM Room r2, RoomReservation rr " +
		             "WHERE r2.id = rr.room.id " +
					 "AND ((rr.checkInDate <= ?1 AND rr.checkOutDate >= ?1) " +
		             "OR  (rr.checkInDate < ?2 AND rr.checkOutDate >= ?2) " +
					 "OR  (?1 <= rr.checkInDate AND ?2 >= rr.checkInDate)))")
	ArrayList<Hotel> findByRoomAvailability(Date checkIn, Date checkOut);

}
