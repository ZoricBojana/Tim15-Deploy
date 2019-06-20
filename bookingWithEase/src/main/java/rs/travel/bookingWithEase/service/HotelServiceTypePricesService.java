package rs.travel.bookingWithEase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.HotelServiceTypePrices;
import rs.travel.bookingWithEase.repository.IHotelServiceTypePricesRepository;

@Service
public class HotelServiceTypePricesService {
	
	@Autowired
	private IHotelServiceTypePricesRepository serviceTypePrices;
	
	public HotelServiceTypePrices save(HotelServiceTypePrices serviceTypePrice) {
		return  serviceTypePrices.save( serviceTypePrice);
	}
	
}
