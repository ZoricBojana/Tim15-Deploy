package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.ConfirmationToken;

public interface IConfTokenRepository extends JpaRepository<ConfirmationToken, Long> {
	
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
