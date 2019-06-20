package rs.travel.bookingWithEase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.Discounts;
import rs.travel.bookingWithEase.repository.IDiscountsRepository;

@Service
public class DiscountsService {
	
	@Autowired
	private IDiscountsRepository discounts;
	
	public Discounts findOne(Long id) {
		Optional<Discounts> discountOpt = discounts.findById(id);
		if (discountOpt.isPresent()) {
			return discountOpt.get();
		}
		return null;
	}

	public List<Discounts> findAll() {
		return discounts.findAll();
	}

	public Discounts save(Discounts disc) {
		
		return discounts.save(disc);
	}

	public void delete(Long id) {
		discounts.deleteById(id);
	}

}
