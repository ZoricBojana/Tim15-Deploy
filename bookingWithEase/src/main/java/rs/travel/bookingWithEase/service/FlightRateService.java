package rs.travel.bookingWithEase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.dto.CompanyRateDTO;
import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.model.FlightRate;
import rs.travel.bookingWithEase.repository.IFlightRateRepository;
import rs.travel.bookingWithEase.repository.IFlightReservationRepository;

@Service
public class FlightRateService {

	@Autowired
	private IFlightRateRepository flightRateRepository;
	
	@Autowired
	private IFlightReservationRepository flightResRepository;
	
	public FlightRate saveRate(CompanyRateDTO dto) {
		
		FlightRate fl = new FlightRate();
		fl.setRate(dto.getRate());
		fl.setReservation(flightResRepository.getOne(dto.getReservationId()));
		
		fl = flightRateRepository.save(fl);
		
		return fl;
		
	}
	
	public Double findAverageByFlight(Long flightId) {
		return flightRateRepository.findAverageByFlight(flightId);
	}

	public List<FlightRate> findByReservationFlight(Flight flight) {
		return flightRateRepository.findByReservationFlight(flight);
	}
}
