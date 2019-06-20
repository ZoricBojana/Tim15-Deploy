package rs.travel.bookingWithEase.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import rs.travel.bookingWithEase.dto.QuickVehicleReservationDTO;
import rs.travel.bookingWithEase.model.QuickVehicleReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.User;
import rs.travel.bookingWithEase.model.Vehicle;
import rs.travel.bookingWithEase.model.VehicleReservation;
import rs.travel.bookingWithEase.service.QuickVehicleReservationService;
import rs.travel.bookingWithEase.service.UserService;
import rs.travel.bookingWithEase.service.VehicleService;

@Controller
@RequestMapping(value = "/quickVehicleReservations")
public class QuickVehicleReservationController {

	@Autowired
	private QuickVehicleReservationService quickVehService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private VehicleService vehicleService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<QuickVehicleReservation>> getAll() {

		Collection<QuickVehicleReservation> qvr = quickVehService.getAll();

		return new ResponseEntity<>(qvr, HttpStatus.OK);
	}
	
	/*@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody QuickVehicleReservationDTO dto){
		
		List<QuickVehicleReservation> qvr = quickVehService.dtoToReservations(dto);
	
		for (QuickVehicleReservation quickVehicleReservation : qvr) {
			quickVehService.add(quickVehicleReservation);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}*/
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody QuickVehicleReservationDTO dto){
		
		List<QuickVehicleReservation> qvr = quickVehService.dtoToReservations(dto);
		
		for (QuickVehicleReservation quickVehicleReservation : qvr) {
			quickVehService.update(quickVehicleReservation);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/{qvrId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reserve(@RequestBody Long userId, @PathVariable("qvrId") Long qvrId){
		
		QuickVehicleReservation qvr = quickVehService.getOne(qvrId);
		
		if(qvr == null) {
			return new ResponseEntity<VehicleReservation>(HttpStatus.EXPECTATION_FAILED); //417

		}
		
		User user = userService.findOne(userId);
		
		VehicleReservation res = new VehicleReservation();
		
		res.setCheckInDate(qvr.getCheckInDate());
		res.setCheckOutDate(qvr.getCheckOutDate());
		res.setTotalPrice(qvr.getTotalPrice()/100*(100-qvr.getDiscount()));
		res.setUser((RegisteredUser) user);
		res.setVehicle(qvr.getVehicle());
		
		Vehicle veh = res.getVehicle();
		try {
			
			if(vehicleService.saveReservation(veh.getId(), res) == null) {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED); //417
			}
			quickVehService.remove(qvrId);
			//vehResService.save(res);
			
			//userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.CONFLICT);//409
		}
		
		//vehResService.save(res);
		
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
