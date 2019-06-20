package rs.travel.bookingWithEase.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.dto.BookQrrDTO;
import rs.travel.bookingWithEase.dto.DefiningQrrDTO;
import rs.travel.bookingWithEase.dto.RoomDTO;
import rs.travel.bookingWithEase.dto.RoomSearchDTO;
import rs.travel.bookingWithEase.model.HotelSpecialOffer;
import rs.travel.bookingWithEase.model.QuickRoomReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.Room;
import rs.travel.bookingWithEase.model.RoomReservation;
import rs.travel.bookingWithEase.model.RoomReservationDate;
import rs.travel.bookingWithEase.repository.IHotelSpecialOfferRepository;
import rs.travel.bookingWithEase.repository.IQuickRoomReservationRepository;
import rs.travel.bookingWithEase.repository.IRoomReservationDate;
import rs.travel.booking_with_ease.exceptions.*;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class QuickRoomReservationService {

	@Autowired
	private IQuickRoomReservationRepository quickRoomReservations;

	@Autowired
	private IHotelSpecialOfferRepository hotelSpecialOffers;
	
	@Autowired 
	private RoomService roomService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private IRoomReservationDate roomResDates;
	
	public QuickRoomReservation findOne(Long id) {
		Optional<QuickRoomReservation> qrrOpt = quickRoomReservations.findById(id);
		if (qrrOpt.isPresent()) {
			
			return qrrOpt.get();
		}
		
		return null;
	}

	public List<QuickRoomReservation> findAll() {
		return quickRoomReservations.findAll();
	}

	public List<QuickRoomReservation> findByUser(RegisteredUser u) {
		return quickRoomReservations.findByUser(u);
	}
	
	@Transactional(readOnly = false)
	public QuickRoomReservation save(QuickRoomReservation roomRes) {
		return quickRoomReservations.save(roomRes);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public QuickRoomReservation saveNew(QuickRoomReservation roomRes) {
		return quickRoomReservations.save(roomRes);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public QuickRoomReservation saveQrr(QuickRoomReservation roomRes) throws MyException {

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

		return save(roomRes);

	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) throws MyException {
		
		Optional<QuickRoomReservation> qrrOpt = quickRoomReservations.findById(id);
		QuickRoomReservation  qrr = null;
		if(qrrOpt.isPresent())
		{
			qrr = qrrOpt.get();
		}
		
		if (qrr == null) {
			throw new MyException("Reservation was not found");
		}
		
		if(qrr.getUser() != null)
		{
			throw new MyException("Reservation was booked!");
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(qrr.getCheckInDate());

		while (!c.getTime().after(qrr.getCheckOutDate())) {
			roomResDates.deleteByRoomIdAndDay(qrr.getRoom().getId(), c.getTime());
			c.add(Calendar.DATE, 1);
		}
		
		quickRoomReservations.delete(qrr);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public QuickRoomReservation bookQrr(Long qrrId, BookQrrDTO dto) throws MyException {
		
		RegisteredUser u = (RegisteredUser) userService.findOne(dto.getId());
		if(u == null)
		{
			// user was not found
			throw new MyException("User with the given id was not found");
		}
		
		QuickRoomReservation qrr = findOne(qrrId);
		
		if(qrr == null)
		{
			throw new MyException("Quick Room Reservation was deleted");
			
		}
		if(qrr.getUser() != null)
		{
			throw new MyException("Quick Room Reservation was booked by another user");
		}
		
		if(qrr.getVersion() != dto.getVersion())
		{
			throw new MyException("Quick Room Reservation was updated");
		}
				
		qrr.setUser(u);
		qrr.setReservationDate(new Date());
		save(qrr);

		return qrr;
	}
	
	@Transactional(readOnly = false)
	public QuickRoomReservation updateQrr(Long qrrId, DefiningQrrDTO qrrDto)
	{
		QuickRoomReservation oldQrr = findOne(qrrId);
		List<QuickRoomReservation> reservations = dtoToReservations(qrrDto);
		QuickRoomReservation newQrr = reservations.get(0);

		oldQrr.setDiscount(newQrr.getDiscount());
		oldQrr.setCheckInDate(newQrr.getCheckInDate());
		oldQrr.setCheckOutDate(newQrr.getCheckOutDate());
		oldQrr.setFinalPrice(newQrr.getFinalPrice());
		oldQrr.setTotalPrice(newQrr.getTotalPrice());
		oldQrr.setSpecialOffers(newQrr.getSpecialOffers());
		
		save(oldQrr);
		return null;
	}
	
	public List<QuickRoomReservation> search(RoomSearchDTO roomSearchDTO) {
		ArrayList<QuickRoomReservation> result1;
		ArrayList<QuickRoomReservation> result2;

		if (roomSearchDTO.getCapacity() == 0 && roomSearchDTO.getFloorNumber() == -11) {
			result1 = quickRoomReservations.findByPriceRange(roomSearchDTO.getHotelId(), roomSearchDTO.getMinPrice(),
					roomSearchDTO.getMaxPrice());

		} else if (roomSearchDTO.getCapacity() == 0) {
			result1 = quickRoomReservations.findByPriceRangeAndFloorNumber(roomSearchDTO.getHotelId(), roomSearchDTO.getFloorNumber(),
					roomSearchDTO.getMinPrice(), roomSearchDTO.getMaxPrice());
		} else if (roomSearchDTO.getFloorNumber() == -11) {
			result1 = quickRoomReservations.findByPriceRangeAndCapacity(roomSearchDTO.getHotelId(), roomSearchDTO.getCapacity(),
					roomSearchDTO.getMinPrice(), roomSearchDTO.getMaxPrice());
		} else {
			result1 = quickRoomReservations.search(roomSearchDTO.getHotelId(), roomSearchDTO.getCapacity(),
					roomSearchDTO.getFloorNumber(), roomSearchDTO.getMinPrice(), roomSearchDTO.getMaxPrice());
		}
		
		// dates
		if (roomSearchDTO.getCheckIn() != null && roomSearchDTO.getCheckOut() != null) {
			result2 = quickRoomReservations.findByCheckInAndCheckOut(roomSearchDTO.getHotelId(), roomSearchDTO.getCheckIn(),
					roomSearchDTO.getCheckOut());
			result1.retainAll(result2); // intersection
		}else if(roomSearchDTO.getCheckIn() != null && roomSearchDTO.getCheckOut() == null)
		{
			result2 = quickRoomReservations.findByCheckIn(roomSearchDTO.getHotelId(), roomSearchDTO.getCheckIn());
			result1.retainAll(result2); // intersection
		}

		
		result1.removeIf(qrr -> (qrr.getUser() != null));
		return result1;
	}

	public List<QuickRoomReservation> dtoToReservations(DefiningQrrDTO dto) {
		
		HashSet<HotelSpecialOffer> soSet = new HashSet<HotelSpecialOffer>();

		for (Long id : dto.getSpecialOffers()) {
			HotelSpecialOffer so = hotelSpecialOffers.getOne(id);
			
			soSet.add(so);
		}
		
		ArrayList<QuickRoomReservation> reservations = new ArrayList<>();
		QuickRoomReservation qrr; 
		
		for(Long roomId : dto.getRooms())
		{	

			Room r = roomService.findById(roomId);
			RoomDTO roomDto = new RoomDTO();
			try {
			  roomDto = roomService.roomToDTO(r);
			}catch(NullPointerException e)
			{
				continue;
			}
			roomService.calculateTotalPrice(roomDto, dto.getCheckIn(), dto.getCheckOut());
					
			qrr =  new QuickRoomReservation(0l, r, null, dto.getCheckIn(), dto.getCheckOut(), soSet, roomDto.getTotalPrice(), dto.getDiscount());
			reservations.add(qrr);

		}

		return reservations;
	}
}
