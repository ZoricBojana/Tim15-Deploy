package rs.travel.bookingWithEase.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.travel.bookingWithEase.dto.BookQrrDTO;
import rs.travel.bookingWithEase.dto.DefiningQrrDTO;
import rs.travel.bookingWithEase.dto.RoomSearchDTO;
import rs.travel.bookingWithEase.model.QuickRoomReservation;
import rs.travel.bookingWithEase.service.QuickRoomReservationService;
import rs.travel.booking_with_ease.exceptions.MyException;

@Controller
@RequestMapping(value = "hotels/{hotelId}/quickRoomReservations")
public class QuickRoomReservationController {

	@Autowired
	private QuickRoomReservationService quickRoomReservationService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<QuickRoomReservation>> getAll() {

		Collection<QuickRoomReservation> quickRoomRes = quickRoomReservationService.findAll();

		return new ResponseEntity<>(quickRoomRes, HttpStatus.OK);
	}

	@GetMapping(value = "/notBooked", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<QuickRoomReservation>> getAllNotBooked() {

		Collection<QuickRoomReservation> quickRoomRes = quickRoomReservationService.findAll();

		quickRoomRes.removeIf(x -> (x.getUser() != null));
		return new ResponseEntity<>(quickRoomRes, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMINHOTEL')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<QuickRoomReservation>> create(@RequestBody DefiningQrrDTO roomResDTO) {
		List<QuickRoomReservation> reservations = quickRoomReservationService.dtoToReservations(roomResDTO);

		if (roomResDTO.getDiscount() < 1 || roomResDTO.getDiscount() > 100
				|| roomResDTO.getCheckIn().after(roomResDTO.getCheckOut())
				|| roomResDTO.getCheckIn().before(new Date())) {
			return new ResponseEntity<>(quickRoomReservationService.findAll(), HttpStatus.BAD_REQUEST);
		}

		boolean flag = false;
		for (QuickRoomReservation qrr : reservations) {
			try {
				quickRoomReservationService.saveQrr(qrr);
		
			} catch (Exception e) {
				
				flag = true;
				continue;
			}
		}

		if (flag || reservations.size() != roomResDTO.getRooms().size()) {
			return new ResponseEntity<>(quickRoomReservationService.findAll(), HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(quickRoomReservationService.findAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMINHOTEL')")
	@DeleteMapping(value = "/{qrrId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<QuickRoomReservation>> deleteUserRoomReservation(
			@PathVariable("hotelId") Long hotelId, @PathVariable("qrrId") Long qrrId)
			throws MyException, OptimisticLockException {

		quickRoomReservationService.delete(qrrId);

		return new ResponseEntity<>(quickRoomReservationService.findAll(), HttpStatus.OK);
	}

	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<QuickRoomReservation>> searchRooms(@RequestBody RoomSearchDTO roomSearchDTO) {

		if (roomSearchDTO.getMaxPrice() == 0)
			roomSearchDTO.setMaxPrice(999999);

		Collection<QuickRoomReservation> services = quickRoomReservationService.search(roomSearchDTO);
		return new ResponseEntity<>(services, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@PutMapping(value = "/{qrrId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> bookQrr(@RequestBody BookQrrDTO bookqrrDTO, @PathVariable("qrrId") Long qrrId)
			throws MyException, OptimisticLockException {

		if (bookqrrDTO.getId() == null || qrrId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		quickRoomReservationService.bookQrr(qrrId, bookqrrDTO);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMINHOTEL')")
	@PutMapping(value = "/{qrrId}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuickRoomReservation> updateQrr(@RequestBody DefiningQrrDTO qrrDto,
			@PathVariable("qrrId") Long qrrId) {

		quickRoomReservationService.updateQrr(qrrId, qrrDto);

		return new ResponseEntity<>(quickRoomReservationService.findOne(qrrId), HttpStatus.OK);

	}

}
