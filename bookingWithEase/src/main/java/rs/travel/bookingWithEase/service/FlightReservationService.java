package rs.travel.bookingWithEase.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.Flight;
import rs.travel.bookingWithEase.model.FlightReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.repository.IFlightReservationRepository;

@Service
public class FlightReservationService {

	@Autowired
	private IFlightReservationRepository flightReservationRepository;

	public FlightReservation findById(Long id) {
		Optional<FlightReservation> flightResOpt = flightReservationRepository.findById(id);
		if (flightResOpt.isPresent()) {
			return flightResOpt.get();
		}
		return null;
	}

	public FlightReservation save(FlightReservation flightReservation) {
		return flightReservationRepository.save(flightReservation);
	}

	public FlightReservation findByFUserAndFlightId(RegisteredUser user, Long flightId) {
		return flightReservationRepository.findByFUserAndFlightId(user, flightId);
	}
	
	public List<FlightReservation> findByUser(Long userId){
		return flightReservationRepository.findByUser(userId);
	}

	public List<FlightReservation> findByFlight(Flight f) {
		return flightReservationRepository.findByFlight(f);
	}

	public List<FlightReservation> findByFlightAndCheckInDateBetween(Flight f, Date start, Date end) {
		return flightReservationRepository.findByFlightAndCheckInDateBetween(f, start, end);
	}
}
