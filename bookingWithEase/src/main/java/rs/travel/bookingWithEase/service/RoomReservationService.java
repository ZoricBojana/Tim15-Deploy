package rs.travel.bookingWithEase.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.travel.bookingWithEase.dto.RoomDTO;
import rs.travel.bookingWithEase.dto.RoomReservationDTO;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.Room;
import rs.travel.bookingWithEase.model.RoomReservation;
import rs.travel.bookingWithEase.model.RoomReservationDate;
import rs.travel.bookingWithEase.model.HotelSpecialOffer;
import rs.travel.bookingWithEase.model.QuickRoomReservation;
import rs.travel.bookingWithEase.repository.IHotelSpecialOfferRepository;
import rs.travel.bookingWithEase.repository.IRoomRepository;
import rs.travel.bookingWithEase.repository.IRoomReservationRepository;
import rs.travel.booking_with_ease.exceptions.MyException;
import rs.travel.bookingWithEase.repository.IRoomReservationDate;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class RoomReservationService {

	@Autowired
	private IRoomReservationRepository roomReservations;

	@Autowired
	private IRoomReservationDate roomResDates;

	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private IRoomRepository rooms;

	@Autowired
	private IHotelSpecialOfferRepository hotelSpecialOffers;

	public RoomReservation findOne(Long id) {
		Optional<RoomReservation> rrOpt = roomReservations.findById(id);

		if (rrOpt.isPresent()) {
			return rrOpt.get();
		}

		return null;
	}

	public List<RoomReservation> findAll() {
		return roomReservations.findAll();
	}

	public List<RoomReservation> findByUser(RegisteredUser u) {
		return roomReservations.findByUser(u);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public RoomReservation save(RoomReservation roomRes) throws MyException {

		Room room = roomService.findById(roomRes.getRoom().getId());
		if (room == null) {
			throw new MyException("Room was deleted");
		}

		Calendar c = Calendar.getInstance();
		c.setTime(roomRes.getCheckInDate());

		while (!c.getTime().after(roomRes.getCheckOutDate())) {
			RoomReservationDate rrd = new RoomReservationDate(null, roomRes.getRoom().getId(), c.getTime());
			roomResDates.save(rrd);
			c.add(Calendar.DATE, 1);
		}

		return roomReservations.save(roomRes);

	}

	@Transactional(readOnly = false)
	public void saveQrr(RoomReservation rr) {
		roomReservations.save(rr);
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		roomReservations.deleteById(id);
	}
	
	public Double findIncome(Long racId, Date start, Date end) {
		return roomReservations.findIncome(racId, start, end);
	} 

	@Transactional(readOnly = false)
	public void cancelReservation(Long id) throws MyException {

		RoomReservation rr = roomReservations.getOne(id);

		if (rr == null) {
			throw new MyException("Reservation was deleted");
		}

		if (rr instanceof QuickRoomReservation) {
			rr.setUser(null);
			saveQrr(rr);
			return;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(rr.getCheckInDate());

		while (!c.getTime().after(rr.getCheckOutDate())) {
			roomResDates.deleteByRoomIdAndDay(rr.getRoom().getId(), c.getTime());
			c.add(Calendar.DATE, 1);
		}
		roomReservations.delete(rr);

	}

	public RoomReservation dtoToReservation(RoomReservationDTO dto) {
		// TODO Validation
		RegisteredUser u = null;
		try {
			u = (RegisteredUser) userService.findOne(dto.getUserId());
		} catch (Exception e) {
		}

		Room r = rooms.getOne(dto.getRoomId());
		HashSet<HotelSpecialOffer> soSet = new HashSet<>();

		for (Long id : dto.getSpecialOffers()) {
			HotelSpecialOffer so = hotelSpecialOffers.getOne(id);
			soSet.add(so);
		}

		Room myRoom = roomService.findById(dto.getRoomId());
		RoomDTO roomDto = roomService.roomToDTO(myRoom);
		roomService.calculateTotalPrice(roomDto, dto.getCheckIn(), dto.getCheckOut());

		return new RoomReservation(0l, r, u, dto.getCheckIn(), dto.getCheckOut(), soSet, roomDto.getTotalPrice());
	}
}
