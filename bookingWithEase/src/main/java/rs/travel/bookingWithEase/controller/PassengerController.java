package rs.travel.bookingWithEase.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.travel.bookingWithEase.dto.FlightReservationDTO;
import rs.travel.bookingWithEase.dto.NextPassengerDTO;
import rs.travel.bookingWithEase.dto.PassengersDTO;
import rs.travel.bookingWithEase.model.FlightInvite;
import rs.travel.bookingWithEase.model.FlightReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.Seat;
import rs.travel.bookingWithEase.model.User;
import rs.travel.bookingWithEase.service.FlightInviteService;
import rs.travel.bookingWithEase.service.FlightReservationService;
import rs.travel.bookingWithEase.service.SeatService;
import rs.travel.bookingWithEase.service.UserService;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

	@Autowired
	private FlightReservationService frService;

	@Autowired
	private FlightInviteService fiService;

	@Autowired
	private SeatService seatService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/unfilled", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PassengersDTO> getNextPassenger(@RequestBody NextPassengerDTO nextPassengerDTO) {

		PassengersDTO passenger = new PassengersDTO();

		List<FlightInvite> flightInvitesForSeats = fiService
				.findByReservationIdAndSeatIdIsNot(nextPassengerDTO.getFlightReservationId(), (long) 0);

		List<Long> seatIds = new ArrayList<>();
		List<Seat> posibleSeats = new ArrayList<>();

		for (FlightInvite fi : flightInvitesForSeats) {
			seatIds.add(fi.getSeatId());
		}

		for (Long seatId : nextPassengerDTO.getSelectedSeats()) {
			if (!seatIds.contains(seatId)) {
				Seat currentSeat = seatService.findById(seatId);
				posibleSeats.add(currentSeat);
			}
		}

		if (posibleSeats.isEmpty()) {
			return new ResponseEntity<>(new PassengersDTO(), HttpStatus.OK);
		}

		passenger.setCurrentSeat(posibleSeats.get(0));
		passenger.setPosibleUsers(new ArrayList<RegisteredUser>());

		User inviter = userService.findOne(nextPassengerDTO.getInviterId());
		FlightInvite fiInviter = fiService.findByFriendEmailAndReservationId(inviter.getEmail(),
				nextPassengerDTO.getFlightReservationId());

		if (fiInviter != null && fiInviter.getSeatId() == 0) {
			if (posibleSeats.size() == 1) { // Ostalo jedno sediste za onoga koji pokrece rezervaciju
				fiInviter.setSeatId(posibleSeats.get(0).getId());
				fiInviter.setAccepted(true);
				fiInviter.setPassport(inviter.getPassportNumber());
				fiService.save(fiInviter);

				Seat seat = posibleSeats.get(0);
				seat.setAvailable(false);
				seatService.save(seat);
				
				return new ResponseEntity<PassengersDTO>(new PassengersDTO(), HttpStatus.OK);
			}

			passenger.getPosibleUsers().add((RegisteredUser) inviter);
		}

		List<FlightInvite> flightInvitesForUsers = fiService.findByReservationIdAndAcceptedAndSeatId(nextPassengerDTO.getFlightReservationId(), true, (long) 0); // TODO: // dodati i expirationDate

		for (FlightInvite fi : flightInvitesForUsers) {
			if(inviter.getEmail().equals(fi.getFriendEmail())) {
				continue;
			}
			User user = userService.findByEmail(fi.getFriendEmail());
			passenger.getPosibleUsers().add((RegisteredUser) user);
		}

		return new ResponseEntity<>(passenger, HttpStatus.OK);
	}

	@PostMapping(value = "/reserve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reserve(@RequestBody FlightReservationDTO frDTO) {

		FlightInvite flightInvite;

		if (frDTO.getUserId() != null) {
			User user = userService.findOne(frDTO.getUserId());
			flightInvite = fiService.findByFriendEmailAndReservationId(user.getEmail(), frDTO.getFlightReservationId());
		} else {
			FlightReservation fr = frService.findById(frDTO.getFlightReservationId());

			flightInvite = new FlightInvite();
			flightInvite.setReservation(fr);
			flightInvite.setAccepted(true);
			flightInvite.setExpirationDate(new Date());
			flightInvite.setPassport(frDTO.getPassport());
			flightInvite.setFriendEmail("");
			flightInvite.setFirstname(frDTO.getFirstname());
			flightInvite.setLastname(frDTO.getLastname());
		}

		flightInvite.setSeatId(frDTO.getSeatId());

		fiService.save(flightInvite);
		
		Seat seat = seatService.findById(frDTO.getSeatId());
		seat.setAvailable(false);
		seatService.save(seat);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
