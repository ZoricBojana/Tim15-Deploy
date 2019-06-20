package rs.travel.bookingWithEase.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Hotel extends Company {

	private static final long serialVersionUID = 1L;

	@Column(name = "stars")
	private int stars;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private HotelServiceTypePrices serviceTypePrices = new HotelServiceTypePrices();

	@OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Room> rooms = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<HotelSpecialOffer> specialOffers = new HashSet<>();

	public Hotel() {
		super();
	}

	public Hotel(long id, String name, String address, String description, double rating) {
		super(id, name, address, description, rating);

	}

	public Hotel(long id, String name, String address, String description, double rating, int stars) {
		super(id, name, address, description, rating);

		this.stars = stars;

	}

	public Hotel(Company company) {
		this.id = company.id;
		this.name = company.name;
		this.address = company.address;
		this.latitude = company.latitude;
		this.longitude = company.longitude;
		this.description = company.description;
		this.rating = company.rating;
	}
	
	public HotelServiceTypePrices getServiceTypePrices() {
		return serviceTypePrices;
	}

	public void setServiceTypePrices(HotelServiceTypePrices serviceTypePrices) {
		this.serviceTypePrices = serviceTypePrices;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public Set<HotelSpecialOffer> getSpecialOffers() {
		return specialOffers;
	}

	public void setSpecialOffers(Set<HotelSpecialOffer> specialOffers) {
		this.specialOffers = specialOffers;
	}

}
