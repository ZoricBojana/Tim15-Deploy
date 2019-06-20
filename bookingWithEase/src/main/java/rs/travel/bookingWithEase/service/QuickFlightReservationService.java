package rs.travel.bookingWithEase.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.QuickFlightReservation;
import rs.travel.bookingWithEase.repository.QuickFlightReservationRepository;

@Service
public class QuickFlightReservationService {

	@Autowired
	private QuickFlightReservationRepository qfrRepo;
	
	public Collection<QuickFlightReservation> findByFlightId(Long id) {
		return qfrRepo.findByFlightId(id);
	}

	public QuickFlightReservation save(QuickFlightReservation qfr) {
		return qfrRepo.save(qfr);
	}

	public QuickFlightReservation findById(Long quickFliResId) {
		return qfrRepo.getOne(quickFliResId);
	}

	public Collection<QuickFlightReservation> findByFlightIdAndFUserIsNull(Long id) {
		return qfrRepo.findByFlightIdAndFUserIsNull(id);
	}

	public void delete(Long qfrId) {
		qfrRepo.deleteById(qfrId);		
	}

}
