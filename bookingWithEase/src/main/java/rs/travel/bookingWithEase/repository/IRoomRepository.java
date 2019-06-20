package rs.travel.bookingWithEase.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rs.travel.bookingWithEase.model.Hotel;
import rs.travel.bookingWithEase.model.Room;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {

	ArrayList<Room> findByHotel(Hotel hotel);
	
	@Query("SELECT r FROM Room r WHERE r.hotel.id = ?1 AND r.roomNumber = ?2")
	Room findByRoomNumberAndHotel(Long hotelId, int roomNumber);

	@Query("SELECT r FROM Room r WHERE r.hotel.id = ?1 AND r.capacity = ?2 AND r.floorNumber = ?3"
			+ " AND ?5 >= r.pricePerNight AND ?4 <= r.pricePerNight")
	ArrayList<Room> search(Long hotelId, int capacity, int floorNumber, double minPrice, double maxPrice);

	@Query("SELECT r FROM Room r WHERE  r.hotel.id = ?1 AND r.capacity = ?2 AND ?4 >= r.pricePerNight AND ?3 <= r.pricePerNight")
	ArrayList<Room> findByPriceRangeAndCapacity(Long hotelId, int capacity, double minPrice, double maxPrice);

	@Query("SELECT r FROM Room r WHERE r.hotel.id = ?1 AND r.floorNumber = ?2 AND ?4 >= r.pricePerNight AND ?3 <= r.pricePerNight")
	ArrayList<Room> findByPriceRangeAndFloorNumber(Long hotelId, int floorNumber, double minPrice, double maxPrice);

	@Query("SELECT r FROM Room r WHERE r.hotel.id = ?1 AND ?3 >= r.pricePerNight AND ?2 <= r.pricePerNight")
	ArrayList<Room> findByPriceRange(Long hotelId, double minPrice, double maxPrice);
	
	@Query("SELECT DISTINCT r FROM Room r " +
		    "WHERE r.hotel.id = ?1 AND r.id NOT IN " +
					"(SELECT r2.id FROM Room r2, RoomReservation rr " +
		             "WHERE r2.id = rr.room.id " +
					 "AND ((rr.checkInDate <= ?2 AND rr.checkOutDate >= ?2) " +
		             "OR  (rr.checkInDate < ?3 AND rr.checkOutDate >= ?3) " +
					 "OR  (?2 <= rr.checkInDate AND ?3 >= rr.checkInDate)))")
	ArrayList<Room> findByAvailability(Long hotelId, Date checkIn, Date checkOut);
	
}
