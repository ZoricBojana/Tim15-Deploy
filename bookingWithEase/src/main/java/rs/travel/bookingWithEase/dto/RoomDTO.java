package rs.travel.bookingWithEase.dto;

import java.util.ArrayList;

import rs.travel.bookingWithEase.model.Price;

public class RoomDTO {

	private Long id;
	private int roomNumber;
	private int floorNumber;
	private int capacity;
	private int rating;
	private double pricePerNight;
	private ArrayList<Price> prices;
	private Long hotelId;
	private double totalPrice;

	public RoomDTO() {
		super();
	}
	
	public RoomDTO(Long id, int roomNumber, int floorNumber, int capacity, int rating, double pricePerNight,
			ArrayList<Price> prices, Long hotelId, double totalPrice) {
		super();
		this.id = id;
		this.roomNumber = roomNumber;
		this.floorNumber = floorNumber;
		this.capacity = capacity;
		this.rating = rating;
		this.pricePerNight = pricePerNight;
		this.prices = prices;
		this.hotelId = hotelId;
		this.totalPrice = totalPrice;
	}

	public RoomDTO(Long id, int roomNumber, int floorNumber, int capacity, double pricePerNight, Long hotelId) {
		super();
		this.id = id;
		this.roomNumber = roomNumber;
		this.floorNumber = floorNumber;
		this.capacity = capacity;
		this.pricePerNight = pricePerNight;
		this.hotelId = hotelId;
	}

	public RoomDTO(Long id, int roomNumber, int floorNumber, int capacity, double pricePerNight, Long hotelId,
			ArrayList<Price> prices) {
		super();
		this.id = id;
		this.roomNumber = roomNumber;
		this.floorNumber = floorNumber;
		this.capacity = capacity;
		this.pricePerNight = pricePerNight;
		this.hotelId = hotelId;
		this.prices = prices;
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

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public ArrayList<Price> getPrices() {
		return prices;
	}

	public void setPrices(ArrayList<Price> prices) {
		this.prices = prices;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
