package rs.travel.bookingWithEase.dto;

public class CompanyRateDTO {

	private long reservationId;
	private double rate;
	
	public CompanyRateDTO() {
	}

	public long getReservationId() {
		return reservationId;
	}

	public double getRate() {
		return rate;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
}
