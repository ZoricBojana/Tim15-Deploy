package rs.travel.bookingWithEase.dto;

import java.util.Date;

public class HotelSearchDTO {
	
	private String name;
	private String address;
	private int stars;
	private double rating;
	private Date checkIn;
	private Date checkOut;
	
	public HotelSearchDTO() {
		super();
	}

	public HotelSearchDTO(String name, String address, int stars, double rating, Date checkIn, Date checkOut) {
		super();
		this.name = name;
		this.address = address;
		this.stars = stars;
		this.rating = rating;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}
	
	@Override
	public String toString() {
		return "HotelSearchDTO [name=" + name + ", address=" + address + ", stars=" + stars + ", rating=" + rating
				+ ", checkIn=" + checkIn + ", checkOut=" + checkOut + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	
}
