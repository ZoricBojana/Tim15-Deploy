package rs.travel.bookingWithEase.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.travel.bookingWithEase.model.QuickRoomReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;

public interface IQuickRoomReservationRepository extends JpaRepository<QuickRoomReservation, Long>  {
	
	List<QuickRoomReservation> findByUser(RegisteredUser u);
	
	@Query("SELECT qrr FROM QuickRoomReservation qrr WHERE qrr.room.hotel.id = ?1 AND qrr.room.capacity = ?2 AND qrr.room.floorNumber = ?3"
			+ " AND ?5 >= qrr.finalPrice AND ?4 <= qrr.finalPrice")
	ArrayList<QuickRoomReservation> search(Long hotelId, int capacity, int floorNumber, double minPrice, double maxPrice);

	@Query("SELECT qrr FROM QuickRoomReservation qrr WHERE  qrr.room.hotel.id = ?1 AND qrr.room.capacity = ?2 AND ?4 >= qrr.finalPrice AND ?3 <= qrr.finalPrice")
	ArrayList<QuickRoomReservation> findByPriceRangeAndCapacity(Long hotelId, int capacity, double minPrice, double maxPrice);

	@Query("SELECT qrr FROM QuickRoomReservation qrr WHERE qrr.room.hotel.id = ?1 AND qrr.room.floorNumber = ?2 AND ?4 >= qrr.finalPrice AND ?3 <= qrr.finalPrice")
	ArrayList<QuickRoomReservation> findByPriceRangeAndFloorNumber(Long hotelId, int floorNumber, double minPrice, double maxPrice);

	@Query("SELECT qrr FROM QuickRoomReservation qrr WHERE qrr.room.hotel.id = ?1 AND ?3 >= qrr.finalPrice  AND ?2 <= qrr.finalPrice ")
	ArrayList<QuickRoomReservation> findByPriceRange(Long hotelId, double minPrice, double maxPrice);
	
	@Query("SELECT DISTINCT qrr FROM QuickRoomReservation qrr " +
		    "WHERE qrr.room.hotel.id = ?1 AND qrr.checkInDate = ?2 AND qrr.checkOutDate = ?3")
	ArrayList<QuickRoomReservation> findByCheckInAndCheckOut(Long hotelId, Date checkIn, Date checkOut);
	
	@Query("SELECT DISTINCT qrr FROM QuickRoomReservation qrr " +
		    "WHERE qrr.room.hotel.id = ?1 AND qrr.checkInDate = ?2")
	ArrayList<QuickRoomReservation> findByCheckIn(Long hotelId, Date checkIn);
}
