package rs.travel.bookingWithEase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.ConfirmationToken;
import rs.travel.bookingWithEase.repository.IConfTokenRepository;

@Service
public class ConfTokenService {
	
	@Autowired
	private IConfTokenRepository tokenRep;
	
	public boolean save(ConfirmationToken token) {

		if (tokenRep.findByConfirmationToken(token.getConfirmationToken()) == null) {
			tokenRep.save(token);
			return true;
		} else {
			return false;
		}

	}
	
	public ConfirmationToken findByConfirmationToken(String token) {
		ConfirmationToken ct = tokenRep.findByConfirmationToken(token);
		return ct;
	}
}

