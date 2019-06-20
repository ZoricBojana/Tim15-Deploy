package rs.travel.bookingWithEase.dto;

import java.util.Date;

public class RoomSearchDTO {
	
	private Long hotelId;
	private int capacity;
	private int floorNumber;
	private double minPrice;
	private double maxPrice;
	private Date checkIn;
	private Date checkOut;
	
	public RoomSearchDTO() {
		super();
	}

	public RoomSearchDTO(Long hotelId, int capacity,int floorNumber, double minPrice, double maxPrice, Date checkIn, Date checkOut) {
		super();
		this.hotelId = hotelId;
		this.capacity = capacity;
		this.floorNumber = floorNumber;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}
	
	@Override
	public String toString() {
		return "RoomSearchDTO [hotelId=" + hotelId + ", capacity=" + capacity + ", floorNumber=" + floorNumber
				+ ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", checkIn=" + checkIn + ", checkOut="
				+ checkOut + "]";
	}
	
	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
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
