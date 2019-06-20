package rs.travel.bookingWithEase.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.travel.bookingWithEase.model.Branch;
import rs.travel.bookingWithEase.model.Vehicle;
import rs.travel.bookingWithEase.service.BranchService;
import rs.travel.bookingWithEase.service.VehicleService;

@Controller
@RequestMapping(value = "/branchs")
public class BranchController {

	@Autowired
	private BranchService branchService;

	@Autowired
	private VehicleService vehicleService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Branch>> getAll() {

		Collection<Branch> branches = branchService.findAll();

		return new ResponseEntity<>(branches, HttpStatus.OK);
	}

	
	@PreAuthorize("hasRole('ADMINRAC')")
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> update(@RequestBody Branch br) {

		if(br.getName().trim().equals("") || br.getName()==null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Branch branch = branchService.findOne(br.getId());

		if (branch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		branch.setName(br.getName());
		branch.setAddress(br.getAddress());

		Branch branch2 = branchService.save(branch);
		return new ResponseEntity<>(branch2, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> findOne(@PathVariable("id") Long id) {

		Branch br = branchService.findOne(id);

		return new ResponseEntity<>(br, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Vehicle>> getMyVehicles(@PathVariable("id") Long id) {
		Branch branch = branchService.findOne(id);

		return new ResponseEntity<>(branch.getVehicles(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMINRAC')")
	@PostMapping(value = "/{id}/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> addVehicle(@PathVariable("id") Long id, @RequestBody Vehicle vehicle) {

		if(vehicle.getRegistrationNumber().trim().equals("") || vehicle.getRegistrationNumber() == null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if(vehicleService.findByRegNumber(vehicle.getRegistrationNumber()) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		Branch branch = branchService.findOne(id);
		branch.addVehicle(vehicle);
		vehicle.setBranch(branch);
		Vehicle veh = vehicleService.save(vehicle);
		branchService.save(branch);

		return new ResponseEntity<>(veh, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMINRAC')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {

		Branch branch = branchService.findOne(id);
		if (branch != null) {
			branchService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
