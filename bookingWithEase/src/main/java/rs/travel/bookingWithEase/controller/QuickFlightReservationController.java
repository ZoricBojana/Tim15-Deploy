package rs.travel.bookingWithEase.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.travel.bookingWithEase.dto.QuickFlightReservationDTO;
import rs.travel.bookingWithEase.dto.QuickFlightReservationShowDTO;
import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.model.FlightInvite;
import rs.travel.bookingWithEase.model.QuickFlightReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.Seat;
import rs.travel.bookingWithEase.service.EmailSenderService;
import rs.travel.bookingWithEase.service.FlightInviteService;
import rs.travel.bookingWithEase.service.FlightService;
import rs.travel.bookingWithEase.service.QuickFlightReservationService;
import rs.travel.bookingWithEase.service.SeatService;
import rs.travel.bookingWithEase.service.UserService;

@Controller
@RequestMapping(value = "/quickFlightReservation")
public class QuickFlightReservationController {

	@Autowired
	private QuickFlightReservationService quickFlightReservationService;

	@Autowired
	private FlightService flightService;

	@Autowired
	private FlightInviteService flightInviteService;

	@Autowired
	private SeatService seatService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailSenderService emailService;

	@Autowired
	private Environment env;

	@GetMapping(value = "/{airlineId}/quickReservations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<QuickFlightReservationShowDTO>> getMyQuickReservations(@PathVariable("airlineId") Long airlineId) {

		return new ResponseEntity<Collection<QuickFlightReservationShowDTO>>(getQFR(airlineId), HttpStatus.OK);
	}
	

	@PostMapping(value = "/quickReservations", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody QuickFlightReservationDTO dto) {

		for (Long seatId : dto.getSeats()) {
			QuickFlightReservation qfr = new QuickFlightReservation();

			Seat seat = seatService.findById(seatId);
			seat.setAvailable(false);
			seat = seatService.save(seat);

			Flight flight = flightService.findOne(seat.getFlight().getId());

			double totalPrice = flight.getPriceTicket() - (flight.getPriceTicket() * dto.getDiscount()) / 100;

			qfr.setCheckInDate(new Date());
			qfr.setCheckOutDate(flight.getDateLand());
			qfr.setDiscount(dto.getDiscount());
			qfr.setFlight(flight);
			qfr.setTotalPrice(totalPrice);

			FlightInvite invite = new FlightInvite();
			invite.setAccepted(false);
			invite.setExpirationDate(new Date());
			invite.setReservation(qfr);
			invite.setSeatId(seatId);

			try {
				quickFlightReservationService.save(qfr);

				flightInviteService.save(invite);
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{airlineId}/quickReservations/{qfrId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<QuickFlightReservationShowDTO>> delete(@PathVariable("airlineId") Long airlineId, @PathVariable("qfrId") Long qfrId) {
		
		quickFlightReservationService.delete(qfrId);
		
		return new ResponseEntity<>(getQFR(airlineId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{quickFliResId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QuickFlightReservation> reserve(@PathVariable("quickFliResId") Long quickFliResId,
			@PathVariable("userId") Long userId) throws MessagingException {

		RegisteredUser regUser = (RegisteredUser) userService.findOne(userId);
		QuickFlightReservation qfr = quickFlightReservationService.findById(quickFliResId);

		qfr.setFUser(regUser);

		FlightInvite invite = flightInviteService.findByReservationId(quickFliResId);
		invite.setAccepted(true);
		invite.setFirstname(regUser.getFirstName());
		invite.setLastname(regUser.getLastName());
		invite.setFriendEmail(regUser.getEmail());
		invite.setPassport(regUser.getPassportNumber());

		quickFlightReservationService.save(qfr);
		flightInviteService.save(invite);

		String mailText = regUser.getFirstName() + " " + regUser.getLastName() + " " + invite.getSeatId() + " "
				+ regUser.getPassportNumber() + "\n";
		double price = qfr.getTotalPrice();

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		 mailMessage.setTo(regUser.getEmail());
		mailMessage.setSubject("Quick reservation #" + quickFliResId);
		mailMessage.setFrom(env.getProperty("spring.mail.username"));
		mailMessage.setText("Your reservation #" + quickFliResId + ": \n\n" + mailText + "Price: " + price);

		emailService.sendEmail(mailMessage);

		return new ResponseEntity<QuickFlightReservation>(qfr, HttpStatus.OK);
	}

	private Collection<QuickFlightReservationShowDTO> getQFR(Long airlineId) {
		Collection<QuickFlightReservationShowDTO> quickFliResReturn = new ArrayList<QuickFlightReservationShowDTO>();

		for (Flight flight : flightService.findAllByAirlineId(airlineId)) {
			for (QuickFlightReservation qfr : quickFlightReservationService.findByFlightIdAndFUserIsNull(flight.getId())) {// TODO: dodati checkInDate < new Date()
				FlightInvite invite = flightInviteService.findByReservationId(qfr.getId());
				
				quickFliResReturn.add(new QuickFlightReservationShowDTO(qfr.getId(), qfr.getTotalPrice(),
						qfr.getDiscount(), flight.getPriceTicket(), flight.getDateFligh(), invite.getSeatId(),
						flight.getStartD(), flight.getFinalD(), flight.getNumber()));
			}
		}
		
		return quickFliResReturn;
	}
}
