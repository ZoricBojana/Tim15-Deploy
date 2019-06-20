package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.Authority;

public interface IAuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(String name);
}
