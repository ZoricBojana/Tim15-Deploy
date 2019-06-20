package rs.travel.bookingWithEase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.FlightReservation;
import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.repository.IRegisteredUserRepository;

@Service
public class RegisteredUserService {

	@Autowired
	private IRegisteredUserRepository regisUserRepository;

	public RegisteredUser findByFlightReservationId(Long flightReservationId) {
		return regisUserRepository.findByFlightReservationsId(flightReservationId);
	}

	public RegisteredUser findByFlightReservationId(FlightReservation fRes) {
		return regisUserRepository.findByFlightReservations(fRes);
	}
	
	
}
