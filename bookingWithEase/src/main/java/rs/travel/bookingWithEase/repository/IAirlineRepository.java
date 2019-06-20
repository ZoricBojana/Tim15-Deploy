package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.Airline;

public interface IAirlineRepository extends JpaRepository<Airline, Long>{
	
/*	Collection<Airline> findAll();

	Airline create(Company company);
	
	Airline find(Long id);
	
	Airline update(Airline airline);*/
	Airline findOneByName(String name);
	
}
