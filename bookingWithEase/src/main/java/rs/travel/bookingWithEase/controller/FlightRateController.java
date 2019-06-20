package rs.travel.bookingWithEase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.travel.bookingWithEase.dto.CompanyRateDTO;
import rs.travel.bookingWithEase.model.FlightRate;
import rs.travel.bookingWithEase.service.FlightRateService;

@Controller
@RequestMapping(value = "flight/rate")
public class FlightRateController {

	@Autowired
	private FlightRateService flRateService;
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FlightRate> save(@RequestBody CompanyRateDTO dto){
		
		FlightRate rate = flRateService.saveRate(dto);
	
		return new ResponseEntity<>(rate, HttpStatus.OK);
	}
}
