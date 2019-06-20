package rs.travel.bookingWithEase.service;

import java.util.Collection;

import rs.travel.bookingWithEase.model.Airline;
import rs.travel.bookingWithEase.model.Company;

public interface IAirlineService {
	
	Collection<Airline> findAll();
	
	Airline find(Long id);
	
	Airline create(Company company) throws Exception;
	
	Airline update(Airline airline) throws Exception;
}
