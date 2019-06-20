package rs.travel.bookingWithEase.service;

import java.util.Collection;


import rs.travel.bookingWithEase.model.Flight;

public interface IFlightService {
	Flight create(Flight flight) throws Exception;
	
	public boolean addFlight(Flight newFlight);
	public boolean delFlight(Flight flightForDelete);
	public boolean editFlight(Flight updateFlight);
	public Collection<Flight> deliveryFlight();
	public Flight searchFlight(String idFlight);
}
