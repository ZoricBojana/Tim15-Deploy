package rs.travel.bookingWithEase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.dto.CompanyRateDTO;
import rs.travel.bookingWithEase.dto.TimeDTO;
import rs.travel.bookingWithEase.model.VehicleRate;
import rs.travel.bookingWithEase.repository.IVehicleRateRepository;
import rs.travel.bookingWithEase.repository.IVehicleReservationRepository;

@Service
public class VehicleRateService {

	@Autowired
	private IVehicleRateRepository vehRateRepository;
	
	@Autowired
	private IVehicleReservationRepository vehResRepository;
	
	public double getAverageByVehicle(Long vehicleId) {
		Double rating = vehRateRepository.findAverageByVehicle(vehicleId);
		
		if(rating == null) {
			return -1;
		}
		
		return rating.doubleValue();
	}
	
	public VehicleRate saveRate(CompanyRateDTO dto) {
		
		VehicleRate veh = new VehicleRate();
		veh.setRate(dto.getRate());
		veh.setReservation(vehResRepository.getOne(dto.getReservationId()));
		
		veh = vehRateRepository.save(veh);
		
		return veh;
		
	}
	
	public Double getAvgByCompany(Long id) {
		Double rating = vehRateRepository.findAverageByCompany(id);
		if(rating == null) {
			rating = -1.0;
		}
		return rating;
	}
	
	
	public Double getAvgByCompanyPeriod(Long id, TimeDTO dto) {
		Double rating = vehRateRepository.findAverageByCompanyPeriod(id, dto.getStart(), dto.getEnd());
		if(rating == null) {
			rating = 1-.0;
		}
		return rating;
	}
	
	public Double getAvgByVehiclePeriod(Long id, TimeDTO dto) {
		Double rating = vehRateRepository.findAverageByVehiclePeriod(id, dto.getStart(), dto.getEnd());
		if(rating == null) {
			rating = -1.0;
		}
		return rating;
	}
}
