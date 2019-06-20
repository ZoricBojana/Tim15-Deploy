/*package rs.travel.bookingWithEase.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import rs.travel.bookingWithEase.model.Airline;
import rs.travel.bookingWithEase.model.Company;

@Repository
public class AirlineRepository implements IAirlineRepository {
	
	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Airline> airlines = new ConcurrentHashMap<Long, Airline>();
	
	public AirlineRepository()
	{
		airlines.put(100l, new Airline(100l,"Airline 1","Beograd"," ",5));
		airlines.put(200l, new Airline(200l,"Airline 2","Beograd"," ",5));
		
	}
	
	
	@Override
	public Collection<Airline> findAll() {
		return this.airlines.values();
	}

	@Override
	public Airline create(Company company) {
		Airline airline = new Airline(company);
		Long id = airline.getId();

		if (id == null) {
			id = counter.incrementAndGet();
			airline.setId(id);
		}
		this.airlines.put(id, airline);

		return airline;
	}
	
	@Override
	public Airline find(Long id) {
		System.out.println("Id je: " + id);
		return this.airlines.get(id);
	}
	
	@Override
	public Airline update(Airline airline) {

		Long id = airline.getId();

		if (id != null) {
			this.airlines.put(id, airline);
		}

		return airline;
	}

}*/
