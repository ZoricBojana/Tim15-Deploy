package rs.travel.bookingWithEase.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.travel.bookingWithEase.dto.AirlineDTO;
import rs.travel.bookingWithEase.dto.AirlineIncomeDTO;
import rs.travel.bookingWithEase.dto.FlightRateDTO;
import rs.travel.bookingWithEase.model.Airline;
import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.model.FlightReservation;
import rs.travel.bookingWithEase.service.AirlineService;
import rs.travel.bookingWithEase.service.FlightRateService;
import rs.travel.bookingWithEase.service.FlightReservationService;
import rs.travel.bookingWithEase.service.FlightService;


@CrossOrigin
@RestController
@RequestMapping(value = "/airlines")
public class AirlineController {

	@Autowired
	private AirlineService airlineService;
	
	@Autowired
	private FlightRateService flightRateService;
	
	@Autowired
	private FlightReservationService flightResSevice;
	
	@Autowired
	private FlightService flightService;

	@PreAuthorize("hasRole('ADMINAIRLINE')")
	@GetMapping("/secured/all")
	public String securedHello() {
		return "Secured Hello";
	}

	@GetMapping("/secured/alternate")
	public String alternate() {
		return "alternate";
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Airline>> getAll() {
		Collection<Airline> airlines = airlineService.findAll();
		return new ResponseEntity<>(airlines, HttpStatus.OK);
	}

	@GetMapping(value = "/airlines", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Airline>> getAirlines() {
		List<Airline> airlines = airlineService.findAll();
		return new ResponseEntity<>(airlines, HttpStatus.OK);
	}

	@GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineDTO> getAirline(@PathVariable("id") Long id) {
	
		Airline airline = airlineService.findOne(id);
		
		if (airline == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new AirlineDTO(airline), HttpStatus.OK);
	}

	@PostMapping(value = "/airlines", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAirline(@RequestBody Airline airline) {

		if (airlineService.findOneByName(airline.getName()) == null) {
			Airline returnAirline = airlineService.save(airline);
			return new ResponseEntity<>(returnAirline, HttpStatus.CREATED);
		}

		return new ResponseEntity<>("Airline with that name already exists!", HttpStatus.FORBIDDEN);
	}

	@PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> update(@RequestBody Airline airline) {
		
		Airline air = airlineService.save(airline);
		
		return new ResponseEntity<>(air, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Airline> deleteAirline(@PathVariable("id") Long id) {
		airlineService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping(value = "/search",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Airline>> search(@RequestBody AirlineDTO airline) {
		Collection<Airline> services = airlineService.search(airline);
		return new ResponseEntity<>(services, HttpStatus.OK);
	}

	@GetMapping(value = "/flights/rate/{airlineId}")
	public ResponseEntity<List<FlightRateDTO>> getRate(@PathVariable("airlineId") Long airlineId) {
	
		List<Flight> flights = flightService.findByAirlineIdAndDateLandLessThan(airlineId, new Date());
		
		if (flights == null || flights.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<FlightRateDTO> flightRates = new ArrayList<FlightRateDTO>();
		
		for(Flight flight : flights) {
			/*
			Double rate = (double) 0;
			List<FlightRate> flightsRate = flightRateService.findByReservationFlight(flight);
			for(FlightRate fr : flightsRate) {
				rate += fr.getRate();
			}
			 */			
			flightRates.add(new FlightRateDTO(flight.getNumber(), flightRateService.findAverageByFlight(flight.getId())));
		}
		
		return new ResponseEntity<>(flightRates, HttpStatus.OK);
	}
	
	@PostMapping(value = "/income",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> airlineIncome(@RequestBody AirlineIncomeDTO airlineIncomeDTO) {
		Airline airline = airlineService.findOne(airlineIncomeDTO.getAirlineId());
		
		Double income = (double) 0;
		List<Flight> flights = flightService.findAllByAirlineId(airline.getId());
		
		for(Flight f : flights) {
			List<FlightReservation> ress = flightResSevice.findByFlightAndCheckInDateBetween(f, airlineIncomeDTO.getStart(), airlineIncomeDTO.getEnd());
			
			for(FlightReservation res : ress) {
				income += res.getTotalPrice();
			}
		}
		
		return new ResponseEntity<>(income, HttpStatus.OK);
	}
}
