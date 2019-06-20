package rs.travel.bookingWithEase.dto;

import java.util.Date;

public class QuickFlightReservationShowDTO {

	private Long qfrId;
	private String flightNumber;
	
	private double currentPrice;
	private Integer discount;
	private double originalPrice;

	private Date dateFligh;
	private Long seatId;

	private String startD;
	private String finalD;

	public QuickFlightReservationShowDTO() {
		super();
	}
	
	public QuickFlightReservationShowDTO(Long id, double currentPrice, Integer discount, double originalPrice, Date dateFligh,
			Long seatId, String startD, String finalD, String flightNumber) {
		super();
		this.qfrId = id;
		this.currentPrice = currentPrice;
		this.discount = discount;
		this.originalPrice = originalPrice;
		this.dateFligh = dateFligh;
		this.seatId = seatId;
		this.startD = startD;
		this.finalD = finalD;
		this.flightNumber = flightNumber;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Date getDateFligh() {
		return dateFligh;
	}

	public void setDateFligh(Date dateFligh) {
		this.dateFligh = dateFligh;
	}

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public String getStartD() {
		return startD;
	}

	public void setStartD(String startD) {
		this.startD = startD;
	}

	public String getFinalD() {
		return finalD;
	}

	public void setFinalD(String finalD) {
		this.finalD = finalD;
	}

	public Long getQfrId() {
		return qfrId;
	}

	public void setQfrId(Long qfrId) {
		this.qfrId = qfrId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

}
