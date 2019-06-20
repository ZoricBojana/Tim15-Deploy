package rs.travel.bookingWithEase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.travel.bookingWithEase.dto.CompanyRateDTO;
import rs.travel.bookingWithEase.model.HotelRate;
import rs.travel.bookingWithEase.service.RoomRateService;

@Controller
@RequestMapping(value = "/room/rate")
public class RoomRateController {

	@Autowired
	private RoomRateService roomRateService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HotelRate> save(@RequestBody CompanyRateDTO dto){
		
		HotelRate rate = roomRateService.saveRate(dto);
	
		return new ResponseEntity<>(rate, HttpStatus.OK);
	}
}
