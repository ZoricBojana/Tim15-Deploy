package rs.travel.bookingWithEase.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@JsonIgnoreProperties(value = { "hotel", "reservations" })
public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "roomNumber", nullable = false)
	private int roomNumber;

	@Column(name = "floorNumber")
	private int floorNumber;

	@Column(name = "capacity")
	private int capacity;

	@Column(name = "rating")
	private int rating;

	@Column(name = "pricePerNigth")
	private double pricePerNight;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Price> prices = new HashSet<>();

	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RoomReservation> reservations = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	private Hotel hotel;
	
	public Room() {
		super();
	}

	public Room(int roomNumber, int floorNumber, int capacity, double pricePerNight, Hotel hotel) {
		super();
		this.roomNumber = roomNumber;
		this.floorNumber = floorNumber;
		this.capacity = capacity;
		this.pricePerNight = pricePerNight;
		this.hotel = hotel;
	}

	public Room(Long id, int roomNumber, int floorNumber, int capacity, double pricePerNight, Hotel hotel) {
		super();
		this.id = id;
		this.roomNumber = roomNumber;
		this.floorNumber = floorNumber;
		this.capacity = capacity;
		this.pricePerNight = pricePerNight;
		this.hotel = hotel;
	}

	@Override
	public String toString() {
		String s = "";
		for (Price p : prices) {
			s += p.getPrice() + " " + p.getStartDate() + " " + p.getEndDate() + "\n";
		}

		return "Room [id=" + id + ", roomNumber=" + roomNumber + ", floorNumber=" + floorNumber + ", capacity="
				+ capacity + ", rating=" + rating + ", pricePerNight=" + pricePerNight + ", prices=" + prices
				+ ", reservations=" + reservations + ", hotel=" + hotel + "]" + "\nPrices: " + s;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public Set<RoomReservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<RoomReservation> reservations) {
		this.reservations = reservations;
	}

	public Room(Long id, int roomNumber, int floorNumber, int capacity, int rating) {
		super();
		this.id = id;
		this.roomNumber = roomNumber;
		this.floorNumber = floorNumber;
		this.capacity = capacity;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Set<RoomReservation> getRooms() {
		return reservations;
	}

	public void setRooms(Set<RoomReservation> reservations) {
		this.reservations = reservations;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Set<Price> getPrices() {
		return prices;
	}

	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}
	
	

}
