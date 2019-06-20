package rs.travel.bookingWithEase.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.travel.bookingWithEase.dto.DiscountsDTO;
import rs.travel.bookingWithEase.model.Discounts;
import rs.travel.bookingWithEase.service.DiscountsService;

@RestController
@RequestMapping(value = "/discounts")
public class DiscountsController {
	
	@Autowired
	private DiscountsService discountsService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Discounts>> getAll() {

		return new ResponseEntity<>(discountsService.findAll(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Discounts> update(@RequestBody DiscountsDTO discountsDto) {
		
		
		return null;
	}
}
