package rs.travel.bookingWithEase.controller;

import java.util.Collection;
import java.util.List;

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

import rs.travel.bookingWithEase.dto.SeatAdminDTO;
import rs.travel.bookingWithEase.dto.SeatDTO;
import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.model.Seat;
import rs.travel.bookingWithEase.service.FlightService;
import rs.travel.bookingWithEase.service.SeatService;

@RestController
@RequestMapping(value = "/seats")
public class SeatController {

	@Autowired
	private SeatService seatService;
	
	@Autowired
	private FlightService flightService;

	@PostMapping(value = "/add",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Seat>> add(@RequestBody SeatDTO seatDTO) {

		Flight flight = flightService.findOne(seatDTO.getFlightId());
		List<Seat> seats = seatService.findByFlightId(seatDTO.getFlightId());
		
		Seat newSeat = new Seat();
		newSeat.setAvailable(true);
		newSeat.setFlight(flight);
		newSeat.setSeatNumber(seats.size() + 1);
		newSeat.setType(seatDTO.getType());
		
		seatService.save(newSeat);
		seats.add(newSeat);
		
		return new ResponseEntity<>(seats, HttpStatus.OK);
	}
	
	@PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Seat>> edit(@RequestBody SeatAdminDTO seatsParam) {

		List<Long> seats = seatsParam.getSelectedSeats();
		
		if(seats == null || seats.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		for(Long seatId : seats) {
			Seat seat = seatService.findById(seatId);
			
			seat.setType(seatsParam.getType());
			
			seatService.save(seat);
		}

		Seat seat = seatService.findById(seats.get(0));
		
		return new ResponseEntity<>(seatService.findByFlightId(seat.getFlight().getId()), HttpStatus.OK);
	}
	
	@PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Seat>> delete(@RequestBody SeatAdminDTO seatsParam) {

		List<Long> seats = seatsParam.getSelectedSeats();
		
		if(seats == null || seats.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		for(Long seatId : seats) {
			Seat seat = seatService.findById(seatId);
			
			seat.setAvailable(false);
			seat.setSeatNumber(-1);
			
			seatService.save(seat);
		}

		Seat seat = seatService.findById(seats.get(0));
		
		return new ResponseEntity<Collection<Seat>>(seatService.findByFlightId(seat.getFlight().getId()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/flight/{flightId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Seat>> getSeats(@PathVariable("flightId") Long flightId) {

		List<Seat> seats = seatService.findByFlightId(flightId);

		return new ResponseEntity<>(seats, HttpStatus.OK);
	}

	@GetMapping(value = "/available/{flightId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Seat>> getAvailableSeats(@PathVariable("flightId") Long flightId) {

		List<Seat> seats = seatService.findByFlightIdAndAvailable(flightId, true);

		return new ResponseEntity<>(seats, HttpStatus.OK);
	}
}
