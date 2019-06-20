package rs.travel.bookingWithEase.dto;

public class FlightRateDTO {

	private String flightNumber;
	private Double averageRate;

	public FlightRateDTO() {}
	
	public FlightRateDTO(String flightNumber, Double averageRate) {
		super();
		this.flightNumber = flightNumber;
		this.averageRate = averageRate;
	}
	
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public Double getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(Double averageRate) {
		this.averageRate = averageRate;
	} 
	
	
}
