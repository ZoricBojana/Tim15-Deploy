package rs.travel.bookingWithEase.dto;

import java.util.List;

import rs.travel.bookingWithEase.model.SeatType;

public class SeatAdminDTO {

	private String typeClass;
	private List<Long> selectedSeats;

	public SeatAdminDTO() {
		super();
	}

	public String getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}

	public List<Long> getSelectedSeats() {
		return selectedSeats;
	}

	public void setSelectedSeats(List<Long> selectedSeats) {
		this.selectedSeats = selectedSeats;
	}

	public SeatType getType() {
		switch (typeClass) {
			case "BUSSINES":
				return SeatType.BUSSINES;
			case "FIRST":
				return SeatType.FIRST;
			default:
				return SeatType.ECONOMY;
		}
		
	}
}
