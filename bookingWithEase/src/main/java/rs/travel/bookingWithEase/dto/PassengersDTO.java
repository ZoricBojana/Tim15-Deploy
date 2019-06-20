package rs.travel.bookingWithEase.dto;

import java.util.ArrayList;
import java.util.List;

import rs.travel.bookingWithEase.model.RegisteredUser;
import rs.travel.bookingWithEase.model.Seat;

public class PassengersDTO {

	private Seat currentSeat;
	private ArrayList<RegisteredUser> posibleUsers;

	public PassengersDTO() {
		super();
	}

	public Seat getCurrentSeat() {
		return currentSeat;
	}

	public void setCurrentSeat(Seat currentSeat) {
		this.currentSeat = currentSeat;
	}

	public List<RegisteredUser> getPosibleUsers() {
		return posibleUsers;
	}

	public void setPosibleUsers(ArrayList<RegisteredUser> posibleUsers) {
		this.posibleUsers = posibleUsers;
	}

}
