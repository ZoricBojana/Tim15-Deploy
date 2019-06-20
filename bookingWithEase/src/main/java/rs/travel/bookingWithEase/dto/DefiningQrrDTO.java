package rs.travel.bookingWithEase.dto;

import java.util.Date;
import java.util.List;

public class DefiningQrrDTO {
	
	private Date checkIn;
	private Date checkOut;
	private int discount;
	private List<Long> specialOffers;
	private List<Long> rooms;
	
	public DefiningQrrDTO() {
		super();
	}

	public DefiningQrrDTO(Date checkIn, Date checkOut, int discount, List<Long> specialOffers, List<Long> rooms) {
		super();
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.discount = discount;
		this.specialOffers = specialOffers;
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return "DefiningQrrDto [checkIn=" + checkIn + ", checkOut=" + checkOut + ", discount=" + discount
				+ ", specialOffers=" + specialOffers + ", rooms=" + rooms + "]";
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

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public List<Long> getSpecialOffers() {
		return specialOffers;
	}

	public void setSpecialOffers(List<Long> specialOffers) {
		this.specialOffers = specialOffers;
	}

	public List<Long> getRooms() {
		return rooms;
	}

	public void setRooms(List<Long> rooms) {
		this.rooms = rooms;
	}
}
