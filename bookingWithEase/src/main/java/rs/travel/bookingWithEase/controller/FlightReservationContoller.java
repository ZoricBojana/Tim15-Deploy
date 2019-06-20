package rs.travel.bookingWithEase.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.travel.bookingWithEase.dto.AirlineReportDTO;
import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.model.FlightInvite;
import rs.travel.bookingWithEase.model.FlightReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.User;
import rs.travel.bookingWithEase.service.FlightInviteService;
import rs.travel.bookingWithEase.service.FlightReservationService;
import rs.travel.bookingWithEase.service.FlightService;
import rs.travel.bookingWithEase.service.UserService;

@RestController
@RequestMapping("/flightReservation")
public class FlightReservationContoller {

	@Autowired
	private FlightReservationService flightReservationService;
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FlightInviteService flightInviteService;
	
	@GetMapping(value = "/new/{flightId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@PathVariable("flightId") Long flightId, @PathVariable("userId") Long userId) {

		Flight flight = flightService.findOne(flightId);
		RegisteredUser regUser;
		FlightReservation flightReservation;		
		
		try {
			regUser = (RegisteredUser)userService.findOne(userId);			
		}catch (Exception e) {
			return new ResponseEntity<>("Only registered users can make a reservation!", HttpStatus.BAD_REQUEST);
		}		
		
		try {
			flightReservation = flightReservationService.findByFUserAndFlightId(regUser, flightId);			
		}catch (Exception e) {
			return new ResponseEntity<>("You already have a reservation for this flight!", HttpStatus.BAD_REQUEST);
		}
		
		if(flightReservation != null) {
			return new ResponseEntity<FlightReservation>(flightReservation, HttpStatus.OK);
		}
		
		flightReservation = new FlightReservation();
		flightReservation.setFUser((RegisteredUser) regUser);
		flightReservation.setFlight(flight);	
		flightReservation.setCheckInDate(new Date());
		flightReservationService.save(flightReservation);
		
		FlightInvite flightInvite = new FlightInvite();
		
		flightInvite.setAccepted(true);
		flightInvite.setExpirationDate(new Date());
		flightInvite.setFriendEmail(regUser.getEmail());
		flightInvite.setFirstname(regUser.getFirstName());
		flightInvite.setLastname(regUser.getLastName());		
		flightInvite.setPassport(regUser.getPassportNumber());
		flightInvite.setReservation(flightReservation);
		flightInvite.setSeatId((long) 0);		
		flightInviteService.save(flightInvite);
		
		return new ResponseEntity<FlightReservation>(flightReservation, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<FlightReservation>> findByUser(@PathVariable("userId") Long userId) {

		Collection<FlightReservation> flRess = flightReservationService.findByUser(userId);
		return new ResponseEntity<Collection<FlightReservation>>(flRess, HttpStatus.OK);
	}

	@PostMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> report(@RequestBody AirlineReportDTO dto) {

		dto.setSaleCount(new ArrayList<Integer>());
		dto.setType(new ArrayList<String>());
		
		List<Flight> flights = flightService.findAllByAirlineId(dto.getAirlineId());
		List<FlightReservation> flightRess;
		Date start = getStartDate(dto);
		Date end = getEndDate(dto);
		Map<Integer, String> dayAndTypeMap = getTypeDayMap(dto);

		for (Flight flight : flights) {
			
			flightRess = flightReservationService.findByFlightAndCheckInDateBetween(flight, start, end);
			
			for (FlightReservation flightRes : flightRess) {
			
				String type = getDayType(dayAndTypeMap, dto, flightRes.getCheckInDate());
				Integer count = flightInviteService.findByReservationIdAndAcceptedAndSeatIdIsNot(flightRes.getId(), true, (long) 0).size();
			
				if(dto.getType().contains(type)) {
					Integer temp = dto.getSaleCount().get(dto.getType().indexOf(type));
					temp += count;
					dto.getSaleCount().set(dto.getType().indexOf(type), temp);
				}else {
					dto.getType().add(type);
					dto.getSaleCount().add(count);
				}
			}
		}

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	private Date getStartDate(AirlineReportDTO dto) {
		Calendar cal = Calendar.getInstance();
		
		switch (dto.getTypeSearch()) {
			case "reportWeek":
			case "reportMonth":
				
				cal.set(Calendar.YEAR, dto.getYear());
				cal.set(Calendar.DAY_OF_YEAR, 1);
				
				return cal.getTime();
			default:
				
				cal.set(Calendar.YEAR, dto.getYear());
				cal.set(Calendar.MONTH, dto.getMonth() -1); // 11 = december
				cal.set(Calendar.DAY_OF_MONTH, 1);
				
				return cal.getTime();
		}
	}
	
	private Date getEndDate(AirlineReportDTO dto) {
		Calendar cal = Calendar.getInstance();
		
		switch (dto.getTypeSearch()) {
			case "reportWeek":
			case "reportMonth":

				//set date to last day of 2014
				cal.set(Calendar.YEAR, dto.getYear());
				cal.set(Calendar.MONTH, 11); // 11 = december
				cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
				
				return cal.getTime();
			default:
				
				cal.set(Calendar.YEAR, dto.getYear());
				cal.set(Calendar.MONTH, dto.getMonth() - 1); // 11 = december
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				
				return cal.getTime();
		}
	}
	
	private Map<Integer, String> getTypeDayMap(AirlineReportDTO dto) {
		Calendar cal = Calendar.getInstance();
		Map<Integer, String> retVal = new HashMap<Integer, String>();
		Integer index = 1;
		
		switch (dto.getTypeSearch()) {
			case "reportMonth":
				for(String month : allMonth()) {
					dto.getType().add(month);
					dto.getSaleCount().add(0);
					retVal.put(index, month);					
					index++;
				}
				
				return retVal;
			case "reportWeek":
				int weekInYears = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
				
				for(Integer i=1; i<=weekInYears; i++) {
					dto.getType().add(i.toString());
					dto.getSaleCount().add(0);
					retVal.put(index, i.toString());
					index++;
				}
				
				return retVal;
			default:
				int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				for(Integer i=1; i<=daysInMonth; i++) {
					dto.getType().add(i.toString());
					dto.getSaleCount().add(0);
					retVal.put(index, i.toString());
					index++;
				}
				
				return retVal;
		}
	}
	
	private String[] allMonth() {
		return new String[] {"january", "february", "march", "april", "may", 
				"june", "july", "august", "september", "october", "november", "december"};
	}

	private String getDayType(Map<Integer, String> dayAndTypeMap, AirlineReportDTO dto, Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Calendar cal = Calendar.getInstance();
		Integer index;
		
		switch (dto.getTypeSearch()) {
			case "reportMonth":				
				index = localDate.getMonthValue();
				break;
			case "reportWeek":
				cal.setTime(date);
				index = cal.get(Calendar.WEEK_OF_YEAR);
				break;
			default:
				index = localDate.getDayOfMonth();
		}
		
		return dayAndTypeMap.get(index);
	}
}
