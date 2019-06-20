package rs.travel.bookingWithEase.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import rs.travel.bookingWithEase.dto.AirlineDTO;
//proba
@Component
@Entity
@Table(name = "Airline")
public class Airline extends Company {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "airline", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Flight> flights = new HashSet<>();
	
	
	@OneToMany(mappedBy = "airline", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Destination> destinations = new HashSet<>();
	
	

	
	@OneToMany(mappedBy = "airline", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AirlineCustomerService> airlineCustomerServices = new HashSet<>();

	/*@OneToMany(mappedBy="id", fetch=FetchType.LAZY ,cascade = CascadeType.ALL)
	private Set<Ticket> quickBookingTickets;
	
	

	@Column(name = "average_rating", nullable = true)
	private Float average_rating;
	
	@OneToMany(mappedBy = "airline", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AirlineAdmin> admins;*/

	public Airline() {
		super();
	}

	public Airline(long id, String name, String address, String description, double rating) {
		super(id, name, address, description, rating);

	}
	
	public Airline(long id, String name, String address, String description) {
		super(id, name, address, description);

	}
	
	public Airline(Company company)
	{
		this.id = company.id;
		this.name = company.name;
		this.address = company.address;
		this.latitude = company.latitude;
		this.longitude = company.longitude;
		this.description = company.description;
		this.rating = company.rating;
	}

	@Override
	public String toString() {
		return "Airline [flights=" + flights + ", destinations=" + destinations 
				+ ", airlineCustomerServices=" + airlineCustomerServices + "]";
	}
	
	public Airline(AirlineDTO airlineDTO) {
		this.name = airlineDTO.getName();
		this.address = airlineDTO.getAddress();
		this.description = airlineDTO.getDescription();
	}
}