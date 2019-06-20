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

import rs.travel.bookingWithEase.dto.DestinationDTO;
import rs.travel.bookingWithEase.model.Airline;
import rs.travel.bookingWithEase.model.Destination;
import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.service.AirlineService;
import rs.travel.bookingWithEase.service.DestinationService;
import rs.travel.bookingWithEase.service.FlightService;

@RestController
@RequestMapping(value = "/destination")
public class DestinationController {

	@Autowired
	private DestinationService destinationService;
	
	@Autowired
	private FlightService flightService;

	@Autowired
	private AirlineService airlineService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Destination>> getAll() {

		Collection<Destination> destinations = destinationService.findAll();

		return new ResponseEntity<>(destinations, HttpStatus.OK);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Destination> create(@RequestBody DestinationDTO destinationDto) {

		
		Airline airline = airlineService.findOne(destinationDto.getAirlineId());
		
		Destination des = new Destination();
		des.setName(destinationDto.getName());
		des.setAddress(destinationDto.getAddress());
		des.setAirline(airline);
		
		try {
			
			des = destinationService.save(des);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (des == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(des, HttpStatus.OK);
	}

	@PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Destination> update(@RequestBody DestinationDTO destinationDto) {

		Destination des = destinationService.findOne(destinationDto.getId());
		Airline airline = airlineService.findOne(destinationDto.getAirlineId());
		
		for(Flight f : flightService.findAllByAirlineId(airline.getId())) {
			if(f.getStartD().equals(des.getAddress() + " (" + des.getName() + ")" )) {
				f.setStartD(destinationDto.getAddress() + " (" + destinationDto.getName() + ")");
				flightService.save(f);
			}
			if(f.getFinalD().equals(des.getAddress() + " (" + des.getName() + ")" )) {
				f.setFinalD(destinationDto.getAddress() + " (" + destinationDto.getName() + ")");
				flightService.save(f);
			}
		}
		
		des.setName(destinationDto.getName());
		des.setAddress(destinationDto.getAddress());
		des.setAirline(airline);
		try {
			des = destinationService.save(des);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (des == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(des, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {

		Destination destination = destinationService.findOne(id);

		if (destination != null) {
			destinationService.delete(id);
			;
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/company/{companyId}")
	public ResponseEntity<Collection<Destination>> getDestinationsByCompanyId(@PathVariable Long companyId) {
		
		Collection<Destination> destinations = destinationService.findByAirlineId(companyId);

		return new ResponseEntity<>(destinations, HttpStatus.OK);
	}
}
