package rs.travel.bookingWithEase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import rs.travel.bookingWithEase.model.HotelSpecialOffer;
import rs.travel.bookingWithEase.repository.IHotelSpecialOfferRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED,  isolation = Isolation.READ_COMMITTED, readOnly = true)
public class HotelSpecialOfferService {
	
	@Autowired
	IHotelSpecialOfferRepository specialOffers;

	public HotelSpecialOffer save(HotelSpecialOffer hotelSpecialOffer) {
		return specialOffers.save(hotelSpecialOffer);
	}
	
	public void delete(Long id) {
		specialOffers.deleteById(id);
	}
	
}
