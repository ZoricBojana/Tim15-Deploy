package rs.travel.bookingWithEase.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.travel.bookingWithEase.dto.FlightDTO;
import rs.travel.bookingWithEase.model.Airline;
import rs.travel.bookingWithEase.model.Destination;
import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.service.AirlineService;
import rs.travel.bookingWithEase.service.DestinationService;
import rs.travel.bookingWithEase.service.FlightService;

@RestController
@RequestMapping("/flights")
public class FlightController {

	@Autowired
	private FlightService flightService;

	@Autowired
	private DestinationService destinationService;

	@Autowired
	private AirlineService airlineService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Flight>> getAll() {

		Collection<Flight> flights = flightService.findAll();

		return new ResponseEntity<>(flights, HttpStatus.OK);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flight> create(@RequestBody FlightDTO flightDTO) {
		Destination startDestination = destinationService.findOne(flightDTO.getStartDestinationId());
		Destination endDestination = destinationService.findOne(flightDTO.getEndDestinationId());
		Airline airline = airlineService.findOne(flightDTO.getAirlineId());

		Flight flight = new Flight(flightDTO.getNumber(), flightDTO.getDateFligh(), flightDTO.getDateLand(),
				flightDTO.getTimeTravel(), flightDTO.getLengthTravel(), startDestination.getAddress() + " (" + startDestination.getName() + ")", endDestination.getAddress()+ " (" + endDestination.getName() + ")",
				flightDTO.getPriceTicket(), flightDTO.getInformationLuggage(), airline, startDestination, endDestination);
		try {
			flight = flightService.save(flight);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (flight == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(flight, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/createFlight", method = RequestMethod.POST,
	 * consumes = MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<Flight>
	 * create(@RequestBody FlightDTO flightDTO) { Flight retVal =
	 * flightService.create(new Flight(flightDTO)); return new
	 * ResponseEntity<>(retVal, HttpStatus.CREATED); }
	 */

	@PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Flight> update(@RequestBody FlightDTO flightDTO) {

		Flight flight = flightService.findOne(flightDTO.getId());
		
		if (flight == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		flight.setDateFligh(flightDTO.getDateFligh());
		flight.setDateLand(flightDTO.getDateLand());
		flight.setLengthTravel(flightDTO.getLengthTravel());
		flight.setPriceTicket(flightDTO.getPriceTicket());
		flight.setTimeTravel(flightDTO.getTimeTravel());
		flight.setFinalD(flightDTO.getEndDestination());
		flight.setStartD(flightDTO.getStartDestination());
		Destination startD = destinationService.findOne(flightDTO.getStartDestinationId());
		Destination endD = destinationService.findOne(flightDTO.getEndDestinationId());
		flight.setStartDestination(startD);
		flight.setEndDestination(endD);
		try {
			flightService.save(flight);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		return new ResponseEntity<Flight>(flight, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {

		Flight flight = flightService.findOne(id);

		if (flight != null) {
			flightService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/airline/{id}")
	public ResponseEntity<Collection<Flight>> getAllFlightsByArilineId(@PathVariable Long id) {

		Collection<Flight> flights = flightService.findAllByAirlineId(id);

		return new ResponseEntity<>(flights, HttpStatus.OK);
	}
	
	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Flight>> search(@RequestBody FlightDTO flightDTO) {
		Collection<Flight> services = flightService.search(flightDTO);
		return new ResponseEntity<>(services, HttpStatus.OK);
	}

}