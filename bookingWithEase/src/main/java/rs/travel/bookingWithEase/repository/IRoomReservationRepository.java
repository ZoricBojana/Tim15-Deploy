package rs.travel.bookingWithEase.repository;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.RoomReservation;

@Repository
public interface IRoomReservationRepository extends JpaRepository<RoomReservation, Long> {
	
	ArrayList<RoomReservation> findByUser(RegisteredUser u);
	
	@Query("SELECT rr FROM RoomReservation rr WHERE rr.room.id = ?1")
	ArrayList<RoomReservation> findByRoomId(Long roomId);
	
	@Query("SELECT sum(totalPrice) FROM RoomReservation rr WHERE rr.room.hotel.id = ?1 AND rr.checkOutDate >= ?2 AND rr.checkOutDate <= ?3")
	Double findIncome(Long hotelId, Date start, Date end);
} 
