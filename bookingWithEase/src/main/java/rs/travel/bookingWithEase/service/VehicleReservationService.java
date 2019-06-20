package rs.travel.bookingWithEase.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.VehicleReservation;
import rs.travel.bookingWithEase.repository.IVehicleReservationRepository;

@Service
public class VehicleReservationService {

	@Autowired
	private IVehicleReservationRepository vehicleReservationRepository;
	
	public Optional<VehicleReservation> findOne(Long id){
		return vehicleReservationRepository.findById(id);
	}
	
	public VehicleReservation getOne(Long id){
		return vehicleReservationRepository.getOne(id);
	}
	
	public List<VehicleReservation> findAll(){
		return vehicleReservationRepository.findAll();
	}
	
	public VehicleReservation save(VehicleReservation vehRes) {
		return vehicleReservationRepository.save(vehRes);
	}
	
	public void delete(Long id) {
		vehicleReservationRepository.deleteById(id);
	}
	
	public List<VehicleReservation> findByUser(Long userId){
		return vehicleReservationRepository.findByUser(userId);
	}
	
	public Double findIncome(Long racId, Date start, Date end) {
		return vehicleReservationRepository.findIncome(racId, start, end);
	} 
}
