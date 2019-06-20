package rs.travel.bookingWithEase.service;

import java.util.Collection;

import rs.travel.bookingWithEase.model.User;

public interface IUserService {
	
	Collection<User> findAll();
	User find(Long id);
	User create(User user) throws Exception;
	User findByUsername(String username);
}
