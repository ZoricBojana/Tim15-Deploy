package rs.travel.bookingWithEase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.RACSpecialOffer;
import rs.travel.bookingWithEase.repository.IRACSpecialOfferRepository;

@Service
public class RACSpecialOfferService {
	
	@Autowired
	private IRACSpecialOfferRepository racSpecialOfferRepository;
	
	public RACSpecialOffer findOne(Long id) {
		Optional<RACSpecialOffer> offer = racSpecialOfferRepository.findById(id);
		
		if(offer.isPresent()) {
			return offer.get();
		}
		
		return null;
	}
	
	public List<RACSpecialOffer> findAll(){
		return racSpecialOfferRepository.findAll();
	}
	
	public RACSpecialOffer save(RACSpecialOffer offer) {
		return racSpecialOfferRepository.save(offer);
	}
	
	public void delete(Long id) {
		racSpecialOfferRepository.deleteById(id);
	}
}
