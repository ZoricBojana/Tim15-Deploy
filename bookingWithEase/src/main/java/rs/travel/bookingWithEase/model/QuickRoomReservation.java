package rs.travel.bookingWithEase.model;

import java.util.Date;
import java.util.HashSet;

import javax.persistence.Entity;


@Entity
public class QuickRoomReservation extends RoomReservation {

	private static final long serialVersionUID = 1L;
	
	
	private int discount;
	
	private double finalPrice;

	public QuickRoomReservation() {
		super();
	}

	public QuickRoomReservation(Long id, Room room, RegisteredUser user, Date checkInDate, Date checkOutDate,
			HashSet<HotelSpecialOffer> specialOffers, double totalPrice,int discount) {
		super(id, room, user, checkInDate, checkOutDate, specialOffers, totalPrice);
		
		this.discount = discount;
		this.finalPrice = this.totalPrice - (this.totalPrice*(discount/100.0));
	}
	
	@Override
	public String toString() {
		return "QuickRoomReservation [discount=" + discount + ", finalPrice=" + finalPrice + ", id=" + id + ", room="
				+ room.getRoomNumber() + ", user=" + user + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
				+ ", reservationDate=" + reservationDate + ", specialOffers=" + specialOffers + ", totalPrice="
				+ totalPrice + "]";
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
