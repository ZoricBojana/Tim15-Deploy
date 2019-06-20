package rs.travel.bookingWithEase.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.travel.bookingWithEase.model.RoomReservationDate;

@Repository
public interface IRoomReservationDate extends JpaRepository<RoomReservationDate, Long> {
	
	void deleteByRoomIdAndDay(Long roomId, Date day);
}
