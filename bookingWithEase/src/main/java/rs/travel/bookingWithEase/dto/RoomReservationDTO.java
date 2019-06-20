package rs.travel.bookingWithEase.dto;

import java.util.Date;
import java.util.List;


public class RoomReservationDTO {
	
	private Long id;
	private Long hotelId;
	private Long roomId;
	private Long userId;
	private Date checkIn;
	private Date checkOut;
	private List<Long> specialOffers;
	private double totalPrice;
	
	public RoomReservationDTO() {
		super();
	}

	public RoomReservationDTO(Long id, Long hotelId, Long roomId, Long userId, Date checkIn, Date checkOut, double totalPrice) {
		super();
		this.id = id;
		this.hotelId = hotelId;
		this.roomId = roomId;
		this.userId = userId;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		return "RoomReservationDTO [id=" + id + ", hotelId=" + hotelId + ", roomId=" + roomId + ", userId=" + userId
				+ ", checkIn=" + checkIn + ", checkOut=" + checkOut + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Long> getSpecialOffers() {
		return specialOffers;
	}

	public void setSpecialOffers(List<Long> specialOffers) {
		this.specialOffers = specialOffers;
	}
	
}
