package rs.travel.bookingWithEase.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.travel.bookingWithEase.dto.QuickVehicleReservationDTO;
import rs.travel.bookingWithEase.dto.RACUpdateDTO;
import rs.travel.bookingWithEase.dto.RentACarSearchDTO;
import rs.travel.bookingWithEase.dto.TimeDTO;
import rs.travel.bookingWithEase.dto.VehicleSearchDTO;
import rs.travel.bookingWithEase.model.Branch;
import rs.travel.bookingWithEase.model.QuickVehicleReservation;
import rs.travel.bookingWithEase.model.RACSpecialOffer;
import rs.travel.bookingWithEase.model.RentACar;
import rs.travel.bookingWithEase.model.Vehicle;
import rs.travel.bookingWithEase.service.BranchService;
import rs.travel.bookingWithEase.service.QuickVehicleReservationService;
import rs.travel.bookingWithEase.service.RACService;
import rs.travel.bookingWithEase.service.RACSpecialOfferService;
import rs.travel.bookingWithEase.service.VehicleRateService;
import rs.travel.bookingWithEase.service.VehicleReservationService;
import rs.travel.bookingWithEase.service.VehicleService;

@RestController
@RequestMapping(value = "/rentacars")
public class RentACarController {

	@Autowired
	private RACService rentACarService;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired 
	private RACSpecialOfferService racOfferService;
	
	@Autowired
	private VehicleReservationService vehResService;
	
	@Autowired
	private QuickVehicleReservationService quickVehService;
	
	@Autowired
	private VehicleRateService vehRateService;

	@GetMapping("/all")
	public String hello() {
		return "Hello Youtube";
	}

	@PreAuthorize("hasRole('ADMINRAC')")
	@GetMapping("/secured/all")
	public String securedHello() {
		return "Secured Hello";
	}

	@GetMapping("/secured/alternate")
	    public String alternate() {
	        return "alternate";
	    }
	
	@GetMapping(value = "/{racId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getOne(@PathVariable("racId") Long racId){
		RentACar rac = rentACarService.findOne(racId);
		
		if(rac == null) {
			return new ResponseEntity<RentACar>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<RentACar>(rac, HttpStatus.OK);
		
	}

	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RentACar>> getAll() {

		Collection<RentACar> racs = rentACarService.findAll();

		return new ResponseEntity<>(racs, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMINRAC')")
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> updateRAC(@RequestBody RACUpdateDTO dto){
		
		if(dto.getName().trim().equals("") || dto.getName()==null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		RentACar rac = rentACarService.findOne(dto.getId());

		if (rac == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		rac.setName(dto.getName());
		rac.setAddress(dto.getAddress());
		rac.setLatitude(dto.getLatitude());
		rac.setLongitude(dto.getLongitude());
		rac.setDescription(dto.getDescription());
		
		RentACar rac2 = rentACarService.save(rac);
		return new ResponseEntity<>(rac2, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/branchs",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Branch>> getMyBranchs(@PathVariable("id") Long id) {
		
		RentACar rac = rentACarService.findOne(id);
		
		return new ResponseEntity<>(rac.getBranches(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMINRAC')")
	@PostMapping(value="/{id}/branchs",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> addBranch(@PathVariable("id") Long id, @RequestBody Branch branch) {

		if(branch.getName().trim().equals("") || branch.getName()==null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		RentACar rac = rentACarService.findOne(id);
		rac.addBranch(branch);
		branch.setRac(rac);
		Branch br = branchService.save(branch);
		rentACarService.save(rac);
		
		return new ResponseEntity<>(br, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Vehicle>> getMyVehicles(@PathVariable("id") Long id){
		RentACar rac = rentACarService.findOne(id);
		
		List<Vehicle> vehs = new ArrayList<>();
		
		for (Branch br : rac.getBranches()) {
			for (Vehicle vehicle : br.getVehicles()) {
				vehicle.setRate(vehRateService.getAverageByVehicle(vehicle.getId()));
				vehs.add(vehicle);
			}
		}
		
		return new ResponseEntity<>(vehs, HttpStatus.OK);
	}

	
	/*******************************************   Special offer   */
	
	@GetMapping(value = "/{id}/specialOffers", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<RACSpecialOffer> getRacsOffers(@PathVariable("id") Long id){
		RentACar rac = rentACarService.findOne(id);
		
		return rac.getSpecialOffers();
	}
	
	@GetMapping(value = "/{id}/specialOffers/{offerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RACSpecialOffer getOneRacsOffer(@PathVariable("id") Long id, @PathVariable("offerId") Long offerId){
		RentACar rac = rentACarService.findOne(id);
		for (RACSpecialOffer off : rac.getSpecialOffers()) {
			if(off.getId().equals(offerId)) {
				return off;
			}
		}
		return null;
	}
	
	@PreAuthorize("hasRole('ADMINRAC')")
	@PostMapping(value = "/{id}/specialOffers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RACSpecialOffer> addSpecialOffer(@PathVariable("id") Long id, @RequestBody RACSpecialOffer offer){
		
		if(offer.getName().trim().equals("") || offer.getName() == null || offer.getPrice() == null || offer.getPrice().doubleValue()<0) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		RACSpecialOffer so = null;
		
		try {
			so = racOfferService.save(offer);
			RentACar rac = rentACarService.findOne(id);
			rac.getSpecialOffers().add(so);
			so.setRacservice(rac);
			rentACarService.save(rac);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(so, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMINRAC')")
	@PutMapping(value = "/{id}/specialOffers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RACSpecialOffer> updateOffer(@PathVariable("id") Long id, @RequestBody RACSpecialOffer offer){
		
		if(offer.getName().trim().equals("") || offer.getName()==null || offer.getPrice()== null || offer.getPrice().doubleValue()<0) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		RACSpecialOffer of = racOfferService.findOne(offer.getId());
		
		if (of == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		of.setName(offer.getName());
		of.setDescription(offer.getDescription());
		of.setPrice(offer.getPrice());
		
		RACSpecialOffer of2 = racOfferService.save(of);
		
		return new ResponseEntity<>(of2, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMINRAC')")
	@DeleteMapping(value="/specialOffers/{idOffer}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("idOffer") Long idOffer){
		
		RACSpecialOffer offer = racOfferService.findOne(idOffer);
		
		if(offer != null) {
			racOfferService.delete(idOffer);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	// search
	@PostMapping(value="/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RentACar>> getByNameAndAddress(@RequestBody RentACarSearchDTO rentACar) {
		
		if(rentACar.getPickUp() == null || rentACar.getDropOff() == null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Collection<RentACar> racs = rentACarService.findByNameAndAddress(rentACar.getName(), rentACar.getAddress(), rentACar.getPickUp(), rentACar.getDropOff());

		return new ResponseEntity<>(racs, HttpStatus.OK);
	}
	
	@PostMapping(value = "/vehicles/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Vehicle>> searchVehicles(@RequestBody VehicleSearchDTO vehicleSearchDTO) {
		Collection<Vehicle> vehs = vehicleService.search(vehicleSearchDTO);
		return new ResponseEntity<>(vehs, HttpStatus.OK);
	}
	
	// quick reservations
	
	@GetMapping(value="/{racId}/quickReservations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<QuickVehicleReservation>> getMyQuickReservations(@PathVariable("racId") Long racId) {

		Collection<QuickVehicleReservation> qvr = quickVehService.findByRac(racId);

		return new ResponseEntity<>(qvr, HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ADMINRAC')")
	@PostMapping(value = "/{racId}/quickReservations", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody QuickVehicleReservationDTO dto) {
		List<QuickVehicleReservation> reservations = quickVehService.dtoToReservations(dto);
		for (QuickVehicleReservation qvr : reservations) {
			try {
				quickVehService.add(qvr);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMINRAC')")
	@PostMapping(value= "/{racId}/income")
	public ResponseEntity<Double> getIncome(@PathVariable("racId") Long racId, @RequestBody TimeDTO dto){
		
		Double income = vehResService.findIncome(racId, dto.getStart(), dto.getEnd());
		if(income == null) {
			income = 0.0;
		}
		return new ResponseEntity<>(income, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMINRAC')")
	@PostMapping(value= "/{racId}/avgRate")
	public ResponseEntity<Double> avgCompanyRate(@PathVariable("racId") Long racId){
		Double avg = vehRateService.getAvgByCompany(racId);
		if(avg == null) {
			avg = -1.0;
		}
		return new ResponseEntity<>(avg, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMINRAC')")
	@PostMapping(value= "/{racId}/avgRatePeriod")
	public ResponseEntity<Double> avgCompanyRatePeriod(@PathVariable("racId") Long racId, @RequestBody TimeDTO dto){
		Double avg = vehRateService.getAvgByCompanyPeriod(racId, dto);
		if(avg == null) {
			avg = -1.0;
		}
		return new ResponseEntity<>(avg, HttpStatus.OK);
	}
}
