package rs.travel.bookingWithEase.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.travel.bookingWithEase.dto.RoomReservationDTO;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.Room;
import rs.travel.bookingWithEase.model.RoomReservation;
import rs.travel.bookingWithEase.security.TokenUtils;
import rs.travel.bookingWithEase.service.CustomUserDetailsService;
import rs.travel.bookingWithEase.service.RoomReservationService;
import rs.travel.bookingWithEase.service.RoomService;
import rs.travel.booking_with_ease.exceptions.MyException;

@Controller
@RequestMapping(value = "hotels/{hotelId}/roomReservations")
public class RoomReservationController {

	@Autowired
	private RoomReservationService roomResService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RoomReservation>> getAll() {

		Collection<RoomReservation> roomRes = roomResService.findAll();

		return new ResponseEntity<>(roomRes, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomReservation> create(HttpServletRequest request, @RequestBody RoomReservationDTO roomResDTO) throws MyException {
		
		if (roomResDTO.getHotelId() == null || roomResDTO.getRoomId() == null || roomResDTO.getCheckIn() == null
				|| roomResDTO.getCheckOut() == null || roomResDTO.getId() != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
					
		Room room = roomService.findById(roomResDTO.getRoomId());
		
		if(room == null)
		{
			return new ResponseEntity<>(HttpStatus.GONE);
		}
				
		RoomReservation roomRes = roomResService.dtoToReservation(roomResDTO);
		RoomReservation roomReservation = null;
		System.out.println("Room dto: " + roomResDTO);
		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		RegisteredUser user = (RegisteredUser) this.userDetailsService.loadUserByUsername(username);	
		roomRes.setUser(user);

		
		roomReservation = roomResService.save(roomRes);
		
		if (roomReservation == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(roomReservation, HttpStatus.OK);
	}

}
