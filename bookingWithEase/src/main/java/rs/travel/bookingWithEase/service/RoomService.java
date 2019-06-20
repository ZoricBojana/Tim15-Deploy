package rs.travel.bookingWithEase.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.joda.time.DateTimeComparator;

import rs.travel.bookingWithEase.dto.RoomDTO;
import rs.travel.bookingWithEase.dto.RoomSearchDTO;
import rs.travel.bookingWithEase.model.Hotel;
import rs.travel.bookingWithEase.model.Price;
import rs.travel.bookingWithEase.model.Room;
import rs.travel.bookingWithEase.model.RoomReservation;
import rs.travel.bookingWithEase.repository.IHotelRateRepository;
import rs.travel.bookingWithEase.repository.IRoomRepository;
import rs.travel.bookingWithEase.repository.IRoomReservationRepository;
import rs.travel.booking_with_ease.exceptions.*;

@Service
@Transactional(propagation = Propagation.REQUIRED,  isolation = Isolation.READ_COMMITTED, readOnly = true)
public class RoomService {

	@Autowired
	private IRoomRepository rooms;

	@Autowired
	private IRoomReservationRepository roomReservations;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private IHotelRateRepository hotelRateRepository;

	public Room findById(Long id) {
		Optional<Room> dbRoom = rooms.findById(id);
		if (dbRoom.isPresent()) {
			return dbRoom.get();
		} else {
			return null;
		}
	}

	public List<Room> findAll() {
		return rooms.findAll();
	}

	public List<RoomDTO> search(RoomSearchDTO roomSearchDTO) {
		ArrayList<Room> result1;
		ArrayList<Room> result2;

		if (roomSearchDTO.getCapacity() == 0 && roomSearchDTO.getFloorNumber() == -11) {
			result1 = rooms.findByPriceRange(roomSearchDTO.getHotelId(), roomSearchDTO.getMinPrice(),
					roomSearchDTO.getMaxPrice());

		} else if (roomSearchDTO.getCapacity() == 0) {
			result1 = rooms.findByPriceRangeAndFloorNumber(roomSearchDTO.getHotelId(), roomSearchDTO.getFloorNumber(),
					roomSearchDTO.getMinPrice(), roomSearchDTO.getMaxPrice());
		} else if (roomSearchDTO.getFloorNumber() == -11) {
			result1 = rooms.findByPriceRangeAndCapacity(roomSearchDTO.getHotelId(), roomSearchDTO.getCapacity(),
					roomSearchDTO.getMinPrice(), roomSearchDTO.getMaxPrice());
		} else {
			result1 = rooms.search(roomSearchDTO.getHotelId(), roomSearchDTO.getCapacity(),
					roomSearchDTO.getFloorNumber(), roomSearchDTO.getMinPrice(), roomSearchDTO.getMaxPrice());
		}

		if (roomSearchDTO.getCheckIn() != null && roomSearchDTO.getCheckOut() != null) {
			result2 = rooms.findByAvailability(roomSearchDTO.getHotelId(), roomSearchDTO.getCheckIn(),
					roomSearchDTO.getCheckOut());
			
			result1.retainAll(result2); // intersection
		}

		ArrayList<RoomDTO> result = new ArrayList<>();

		for (Room r : result1) {
			RoomDTO roomDto = roomToDTO(r);
			calculateTotalPrice(roomDto, roomSearchDTO.getCheckIn(), roomSearchDTO.getCheckOut());
			Double rating = hotelRateRepository.findAverageByRoom(r.getId());
			if(rating == null) {
				roomDto.setRating(0);
			}else {
				roomDto.setRating(rating.intValue());
			}
			
			result.add(roomDto);
		}
		
		return result;
	}

	public List<Room> findByHotelId(Long id) {

		List<Room> rs = rooms.findByHotel(hotelService.findOne(id));

		for (Room room : rs) {
			Double rating = hotelRateRepository.findAverageByRoom(room.getId());
			if (rating == null) {
				room.setRating(0);
			} else {
				room.setRating(rating.intValue());
			}

		}

		return rs;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Room addNewRoom(Room room) throws EntityAlreadyExistsException {

		if (rooms.findByRoomNumberAndHotel(room.getHotel().getId(), room.getRoomNumber()) != null) {
			throw new EntityAlreadyExistsException("Room with the same number already exists");
		}

		rooms.save(room);

		return room;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String updateRoom(Room room) throws EntityAlreadyExistsException, EntityNotEditableException, MyException {

		Room oldRoom = rooms.findByRoomNumberAndHotel(room.getHotel().getId(), room.getRoomNumber());
		if(oldRoom == null)
		{
				throw new MyException("Hotel room does not exist");
		}
		if (oldRoom != null) {
			if (oldRoom.getId() != room.getId())
				throw new EntityAlreadyExistsException("Room with the same number already exists");
		}	
		
		ArrayList<RoomReservation> reservations = roomReservations.findByRoomId(room.getId());

		// remove quick reservations which are not booked
		reservations.removeIf(rr -> (rr.getUser() == null));

		if (reservations.size() > 0) {
			throw new EntityNotEditableException("Unable to update the room. The room has its reservations.");
		}

		rooms.save(room);

		return "Room updated";
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(Long id) throws EntityNotEditableException, MyException {
		
		Room r = findById(id);
		if(r == null)
		{
			throw new MyException("Hotel room does not exist");
		}
		
		ArrayList<RoomReservation> reservations = roomReservations.findByRoomId(id);

		// remove quick reservations which are not booked
		reservations.removeIf(rr -> (rr.getUser() == null));

		if (reservations.size() > 0) {
			throw new EntityNotEditableException("Unable to delete the room. The room has its reservations.");
		}
		
		rooms.deleteById(id);
	}

	public Room dtoToRoom(RoomDTO roomDto) {
		Hotel hotel = hotelService.findById(roomDto.getHotelId());
		if (hotel == null) {
			return null;
		}

		HashSet<Price> prices = new HashSet<>();
		prices = roomDto.getPrices() == null ? prices : new HashSet<Price>(roomDto.getPrices());

		Room room = new Room(roomDto.getId(), roomDto.getRoomNumber(), roomDto.getFloorNumber(), roomDto.getCapacity(),
				roomDto.getPricePerNight(), hotel);

		room.setPrices(prices);
		return room;
	}

	public RoomDTO roomToDTO(Room room) {

		return new RoomDTO(room.getId(), room.getRoomNumber(), room.getFloorNumber(), room.getCapacity(),
				room.getRating(), room.getPricePerNight(), new ArrayList<Price>(room.getPrices()),
				room.getHotel().getId(), 0);
	}

	public void calculateTotalPrice(RoomDTO roomDto, Date checkIn, Date checkOut) {
		Date currentD = checkIn;

		double totalPrice = 0;

		if (checkIn != null && checkOut != null) {
			DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
			Calendar c = Calendar.getInstance();
			c.setTime(currentD);
			do {
				boolean flag = false;
				for (Price price : roomDto.getPrices()) {
					if (!currentD.before(price.getStartDate()) && !currentD.after(price.getEndDate())) {
						totalPrice += price.getPrice();

						flag = true;
						break;
					}
				}
				if (!flag) {
					totalPrice += roomDto.getPricePerNight();
			
				}

				c.add(Calendar.DATE, 1);
				currentD = c.getTime();
			} while (dateTimeComparator.compare(currentD, checkOut) != 0);
		}
		roomDto.setTotalPrice(totalPrice);
	}

}
