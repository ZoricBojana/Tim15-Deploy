package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findByEmailIgnoreCase(String email);
}
