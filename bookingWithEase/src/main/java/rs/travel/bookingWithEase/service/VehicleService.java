package rs.travel.bookingWithEase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.travel.bookingWithEase.dto.VehicleSearchDTO;
import rs.travel.bookingWithEase.model.QuickVehicleReservation;
import rs.travel.bookingWithEase.model.Vehicle;
import rs.travel.bookingWithEase.model.VehicleReservation;
import rs.travel.bookingWithEase.repository.IQuickVehicleReservationRepository;
import rs.travel.bookingWithEase.repository.IVehicleRateRepository;
import rs.travel.bookingWithEase.repository.IVehicleRepository;

@Service
@Transactional(readOnly=true)
public class VehicleService {

	@Autowired
	private IVehicleRepository vehicleRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IVehicleRateRepository vehRateRepository;
	
	@Autowired
	private IQuickVehicleReservationRepository qvRepository;

	public Vehicle findOne(Long id) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);
		if (vehicle.isPresent()) {
			
			Double rating = vehRateRepository.findAverageByVehicle(id);

			vehicle.get().setRate(rating);
			
			return vehicle.get();
		}
		return null;
	}

	public List<Vehicle> findAll() {
		List<Vehicle> vehs = vehicleRepository.findAll();
		
		for (Vehicle vehicle : vehs) {
			vehicle.setRate(vehRateRepository.findAverageByVehicle(vehicle.getId()));
			System.out.println("Rating: " + vehicle.getRate());
		}
		return vehs;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public Vehicle save(Vehicle vehicle) {
		logger.info("> create");
		Vehicle savedVehicle = vehicleRepository.save(vehicle);
		if(vehicle.getId() != null) {
			System.out.println("id nije nula");
		}
		logger.info("< create");
		return savedVehicle;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public Vehicle update(Vehicle vehicle) {
		logger.info("> update");
		if(vehicle.getId() == null) {
			System.out.println("id nula");
		}
		Vehicle savedVehicle = vehicleRepository.save(vehicle);
		logger.info("< update");
		return savedVehicle;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void delete(Long id) {
		vehicleRepository.deleteById(id);
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public Vehicle saveReservation(Long id, VehicleReservation vehRes) {
		System.out.println("preopt");
		Optional<Vehicle> optveh = vehicleRepository.findById(id);
		System.out.println("opt");
		if(!optveh.isPresent()) {
			System.out.println("not present");
			return null;
		}
		
		if(vehRes instanceof QuickVehicleReservation) {
			QuickVehicleReservation qvr = qvRepository.getOne(vehRes.getId());
			
			if(qvr == null) {
				return null;
			}
		}
		
		Vehicle veh = optveh.get();
		
		for (VehicleReservation vr : veh.getVehicleReservations()) {
			if ((vehRes.getCheckInDate().before(vr.getCheckInDate())
					&& vehRes.getCheckOutDate().after(vr.getCheckOutDate()))
					|| (vehRes.getCheckInDate().after(vr.getCheckInDate())
							&& vehRes.getCheckInDate().before(vr.getCheckOutDate()))
					|| (vehRes.getCheckOutDate().after(vr.getCheckInDate())
							&& vehRes.getCheckInDate().before(vr.getCheckOutDate()))) {
				
				if(vr instanceof QuickVehicleReservation) {
					continue;
				}
				return null;
			}
		}
		
		veh.getVehicleReservations().add(vehRes);
		vehicleRepository.save(veh);
		return veh;
	}
	
	public Vehicle findByRegNumber(String regNumber) {
		return vehicleRepository.findByRegistrationNumber(regNumber);
	}
	
	public List<Vehicle> search(VehicleSearchDTO dto){
		
		ArrayList<Vehicle> result1;
		ArrayList<Vehicle> result2;
		
		result1 = vehicleRepository.findByTypeContainingIgnoreCaseAndGearContainingIgnoreCase(dto.getType(), dto.getGear());
		double min, max;
		
		if(dto.getMinPrice() == null) {
			min = 0;
		}else {
			min = dto.getMinPrice();
		}
		
		if(dto.getMinPrice() == null) {
			max = 999999999;
		}else {
			max = dto.getMaxPrice();
		}
		
		result2 = vehicleRepository.findByPriceRange(dto.getRentacarId(), min, max);
		
		result1.retainAll(result2);
		
		if(dto.getPickUp()!= null && dto.getDropOff() != null) {
			result2 = vehicleRepository.findByAvailability(dto.getRentacarId(), dto.getPickUp(), dto.getDropOff());
		}
		
		result1.retainAll(result2);
		
		for (Vehicle vehicle : result1) {
			vehicle.setRate(vehRateRepository.findAverageByVehicle(vehicle.getId()));
			System.out.println("Rating search: " + vehicle.getRate());
		}
		
		return result1;
	}
}
