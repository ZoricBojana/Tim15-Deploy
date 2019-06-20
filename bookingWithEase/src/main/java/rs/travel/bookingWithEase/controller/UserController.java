package rs.travel.bookingWithEase.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.travel.bookingWithEase.dto.AccountDTO;
import rs.travel.bookingWithEase.dto.AdminUserDTO;
import rs.travel.bookingWithEase.dto.InviteFriendsFlightDTO;
import rs.travel.bookingWithEase.model.Admin;
import rs.travel.bookingWithEase.model.Authority;
import rs.travel.bookingWithEase.model.ConfirmationToken;
import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.model.FlightInvite;
import rs.travel.bookingWithEase.model.FlightReservation;
import rs.travel.bookingWithEase.model.FriendRequest;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.RoomReservation;
import rs.travel.bookingWithEase.model.User;
import rs.travel.bookingWithEase.model.VehicleReservation;
import rs.travel.bookingWithEase.security.TokenUtils;
import rs.travel.bookingWithEase.service.AuthorityService;
import rs.travel.bookingWithEase.service.ConfTokenService;
import rs.travel.bookingWithEase.service.EmailSenderService;
import rs.travel.bookingWithEase.service.FlightInviteService;
import rs.travel.bookingWithEase.service.FlightReservationService;
import rs.travel.bookingWithEase.service.FlightService;
import rs.travel.bookingWithEase.service.FriendsRequestService;
import rs.travel.bookingWithEase.service.RoomReservationService;
import rs.travel.bookingWithEase.service.UserService;
import rs.travel.bookingWithEase.service.VehicleReservationService;
import rs.travel.booking_with_ease.exceptions.MyException;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoomReservationService roomResService;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private ConfTokenService confTokenService;

	@Autowired
	private EmailSenderService emailService;

	@Autowired
	private Environment env;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authService;

	@Autowired
	private FriendsRequestService friendsRequestService;

	@Autowired
	private FlightReservationService flightReservationService;

	@Autowired
	private FlightInviteService flightInviteService;
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private RoomReservationService roomReservationService;
	
	@Autowired
	private VehicleReservationService vehReservationService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<User> findAll() {
		return userService.findAll();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody AdminUserDTO adminUserDto) {

		Admin newUser = (Admin) userService.dtoToUser(adminUserDto);

		Authority a = null;

		switch (adminUserDto.getAdminType()) {
		case "hotel":
			a = authService.findByName("ROLE_ADMINHOTEL");
			break;
		case "rent-a-car":
			a = authService.findByName("ROLE_ADMINRAC");
			break;
		case "airline":
			a = authService.findByName("ROLE_ADMINAIRLINE");
			break;
		default:
			a = null;
		}

		if (a != null) {
			ArrayList<Authority> alist = new ArrayList<Authority>();
			alist.add(a);
			newUser.setAuthorities(alist);
		}

		if (userService.save(newUser)) {

			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>("Username is already taken", HttpStatus.CONFLICT);
		}

	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> update(@RequestBody AccountDTO dto) throws Exception {

		User user = userService.findByEmail(dto.getEmail());

		user.setFirstName(dto.getName());
		user.setLastName(dto.getLastName());
		user.setCity(dto.getCity());
		user.setTelephoneNumber(dto.getPhoneNumber());

		User upUser = userService.update(user);

		return new ResponseEntity<>(upUser, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('ADMINRAC') or hasRole('ADMINHOTEL') or hasRole('ADMINAIRLINE') or hasRole('USER')")
	@GetMapping(value = "/myprofile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> myProfile(@RequestHeader("Authorization") String authHeader) {
		if (authHeader == null) {
			return null;
		}
	
		String token = null;
		if (authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}

		String username = tokenUtils.getUsernameFromToken(token);

		User user = userService.findByUsername(username);

		return ResponseEntity.ok(user);
	}

	// **************** RESERVATIONS *************

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/{userId}/roomReservations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RoomReservation>> getUserRoomReservations(@PathVariable("userId") Long userId) {
		RegisteredUser u = (RegisteredUser) userService.findOne(userId);
		return new ResponseEntity<>(roomResService.findByUser(u), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@DeleteMapping(value = "/{userId}/roomReservations/{rrId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RoomReservation>> deleteUserRoomReservation(@PathVariable("userId") Long userId,
			@PathVariable("rrId") Long rrId) throws MyException {
		RegisteredUser u = (RegisteredUser) userService.findOne(userId);
		RoomReservation r = roomResService.findOne(rrId);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 2);
		if (cal.getTime().after(r.getCheckInDate())) {

			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		} else {

			roomResService.cancelReservation(rrId);
		}
		return new ResponseEntity<>(roomResService.findByUser(u), HttpStatus.OK);
	}

	// registration

	@PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerUser(@RequestBody AccountDTO account) throws MessagingException {

		if (account.getUsername().trim().isEmpty() || account.getUsername() == null
				|| account.getPassword().trim().isEmpty() || account.getPassword() == null
				|| account.getEmail().trim().isEmpty() || account.getEmail() == null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (!account.getPassword().equals(account.getConfPassword())) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		}

		User existingUser = userService.findByEmail(account.getEmail());
		if (existingUser != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		User user = new User(account);
		user.setPassword(passwordEncoder.encode(account.getPassword()));
		Authority a = authService.findByName("ROLE_USER");
		ArrayList<Authority> alist = new ArrayList<>();
		alist.add(a);
		user.setAuthorities(alist);
		userService.save(user);
		ConfirmationToken confirmationToken = new ConfirmationToken(user);

		confTokenService.save(confirmationToken);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom(env.getProperty("spring.mail.username"));
		mailMessage.setText("To confirm your account, please click here : "
				+ "http://localhost:8080/users/confirm-account?token=" + confirmationToken.getConfirmationToken());

		emailService.sendEmail(mailMessage);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confTokenService.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userService.findByEmail(token.getUser().getEmail());
			user.setEnabled(true);
			Authority a = authService.findByName("ROLE_USER");
			ArrayList<Authority> alist = new ArrayList<>();
			alist.add(a);
			user.setAuthorities(alist);
			userService.update(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>("Your account is enabled!", HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@GetMapping(value = "/friends/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<User>> getAllFriends(@PathVariable("userId") Long userId) {

		List<User> friends = new ArrayList<>();
		HashSet<FriendRequest> userFriends = friendsRequestService.findBySenderId(userId);

		for (FriendRequest friendRequest : userFriends) {
			friends.add(friendRequest.getReciever());
		}

		userFriends = friendsRequestService.findByRecieverId(userId);

		for (FriendRequest friendRequest : userFriends) {
			friends.add(friendRequest.getSender());
		}

		return new ResponseEntity<>(friends, HttpStatus.OK);
	}

	@PostMapping(value = "/inviteFriends", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> inviteFriends(@RequestBody InviteFriendsFlightDTO invFriendsFlDTO)
			throws MessagingException {

		FlightReservation flightReservation = flightReservationService
				.findById(invFriendsFlDTO.getFlightReservationId());

		SimpleMailMessage mailMessage;

		for (Long friendId : invFriendsFlDTO.getInvFriends()) {
			User friend = userService.findOne(friendId);

			if (flightInviteService.findByFriendEmailAndReservationId(friend.getEmail(),
					flightReservation.getId()) != null) {
				continue;
			}

			FlightInvite flightInvite = new FlightInvite();
			flightInvite.setAccepted(false);

			Date dt = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DATE, 2);
			dt = c.getTime();

			flightInvite.setExpirationDate(dt);
			flightInvite.setFriendEmail(friend.getEmail());
			flightInvite.setFirstname(friend.getFirstName());
			flightInvite.setLastname(friend.getLastName());
			flightInvite.setPassport(friend.getPassportNumber());
			flightInvite.setReservation(flightReservation);
			flightInvite.setSeatId((long) 0);

			flightInviteService.save(flightInvite);

			ConfirmationToken confirmationToken = new ConfirmationToken(friend);
			confTokenService.save(confirmationToken);

			mailMessage = new SimpleMailMessage();
			mailMessage.setTo(friend.getEmail());
			mailMessage.setSubject("Flight invite");
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setText(
					"To confirm invitation, please click here : " + "http://localhost:8080/users/confirm-invite?flight="
							+ flightReservation.getId() + "&token=" + confirmationToken.getConfirmationToken());

			emailService.sendEmail(mailMessage);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/confirm-invite", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> confirmInvitation(@RequestParam("flight") Long flightId,
			@RequestParam("token") String confirmationToken) {

		ConfirmationToken token = confTokenService.findByConfirmationToken(confirmationToken);

		if (token != null) {
			FlightInvite invite = flightInviteService.findByFriendEmailAndReservationId(token.getUser().getEmail(),
					flightId);
			invite.setAccepted(true);

			flightInviteService.save(invite);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>("Your account is enabled!", HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@GetMapping(value = "/sendMailReservation/{fliResId}/{hotelId}/{rentACarId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendMailReservation(@PathVariable("fliResId") Long fliResId, @PathVariable("hotelId") Long hotelId, @PathVariable("rentACarId") Long rentACarId) throws MessagingException {

		FlightReservation flightReservation = flightReservationService.findById(fliResId);
		String roomText = "";
		String vehicleText = "";
		double totalPrice = 0;
		
		if(hotelId != -1) {
			RoomReservation room = roomReservationService.findOne(hotelId);
			roomText = "\n\nRoom-> Number: " + room.getRoom().getRoomNumber() + " | Price: " + room.getTotalPrice();
			totalPrice += room.getTotalPrice();
		}
		
		if(rentACarId != -1) {
			VehicleReservation vehicle = vehReservationService.getOne(rentACarId);
			vehicleText = "\n\nVehicle-> Reg. Number: " + vehicle.getVehicle().getRegistrationNumber() + " | Price: " + vehicle.getTotalPrice();
			totalPrice += vehicle.getTotalPrice();
		}		
		
		Flight flight = flightService.findByFlightReservations(flightReservation);
		List<FlightInvite> flightInvites = flightInviteService.findByReservationIdAndAcceptedAndSeatIdIsNot(fliResId, true, (long) 0);
		
		String mailText = "";
		double price = flight.getPriceTicket()*flightInvites.size();
		totalPrice += price;
		
		for (FlightInvite fi : flightInvites) {
			mailText += fi.getFirstname() + " " + fi.getLastname() + "-> Seat: " + fi.getSeatId() + " | Passport: " + fi.getPassport() + "\n";			
		}
		
		mailText += "Price: " + price;
		mailText += roomText;
		mailText += vehicleText;
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(flightReservation.getFUser().getEmail());
		mailMessage.setSubject("Reservation #" + fliResId);
		mailMessage.setFrom(env.getProperty("spring.mail.username"));
		mailMessage.setText("Your reservation #" + fliResId + ": \n\n"+mailText+"\n\nTotal Price is: "+totalPrice);

		emailService.sendEmail(mailMessage);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
