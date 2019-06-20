package rs.travel.bookingWithEase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.Authority;
import rs.travel.bookingWithEase.repository.IAuthorityRepository;

@Service
public class AuthorityService {
	
	@Autowired
	private IAuthorityRepository authRepository;
	
	public Authority findByName(String name) {
		return authRepository.findByName(name);
	}
}
