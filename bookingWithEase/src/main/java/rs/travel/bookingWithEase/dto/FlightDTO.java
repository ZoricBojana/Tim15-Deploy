package rs.travel.bookingWithEase.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import rs.travel.bookingWithEase.model.Destination;

public class FlightDTO {
	
	private Long id;
	
	private String number;
	
	private Date dateFligh;

	private Date dateLand;

	private int timeTravel;

	private int lengthTravel;
	
	private String startDestination;
	
	private String endDestination;
	
	private double priceTicket;
	
	private String informationLuggage;
    
	private Set<Destination> destinations=new HashSet<>();
	
	private Long airlineId;

	private Long startDestinationId;

	private Long endDestinationId;

	public FlightDTO(Long id, String number, Date dateFligh, Date dateLand, int timeTravel, int lengthTravel,
			String startDestination, String endDestination, double priceTicket, String informationLuggage,
			Set<Destination> destinations, Long airlineId, Long startDestinationId, Long endDestinationId) {
		super();
		this.id = id;
		this.number = number;
		this.dateFligh = dateFligh;
		this.dateLand = dateLand;
		this.timeTravel = timeTravel;
		this.lengthTravel = lengthTravel;
		this.startDestination = startDestination;
		this.endDestination = endDestination;
		this.priceTicket = priceTicket;
		this.informationLuggage = informationLuggage;
		this.destinations = destinations;
		this.airlineId = airlineId;
		this.startDestinationId = startDestinationId;
		this.endDestinationId = endDestinationId;
	}
	
	public FlightDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDateFligh() {
		return dateFligh;
	}

	public void setDateFligh(Date dateFligh) {
		this.dateFligh = dateFligh;
	}

	public Date getDateLand() {
		return dateLand;
	}

	public void setDateLand(Date dateLand) {
		this.dateLand = dateLand;
	}

	public int getTimeTravel() {
		return timeTravel;
	}

	public void setTimeTravel(int timeTravel) {
		this.timeTravel = timeTravel;
	}

	public int getLengthTravel() {
		return lengthTravel;
	}

	public void setLengthTravel(int lengthTravel) {
		this.lengthTravel = lengthTravel;
	}

	public String getStartDestination() {
		return startDestination;
	}

	public void setStartDestination(String startDestination) {
		this.startDestination = startDestination;
	}

	public String getEndDestination() {
		return endDestination;
	}

	public void setEndDestination(String endDestination) {
		this.endDestination = endDestination;
	}

	public double getPriceTicket() {
		return priceTicket;
	}

	public void setPriceTicket(double priceTicket) {
		this.priceTicket = priceTicket;
	}

	public String getInformationLuggage() {
		return informationLuggage;
	}

	public void setInformationLuggage(String informationLuggage) {
		this.informationLuggage = informationLuggage;
	}

	public Set<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(Set<Destination> destinations) {
		this.destinations = destinations;
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public Long getStartDestinationId() {
		return startDestinationId;
	}

	public void setStartDestinationId(Long startDestinationId) {
		this.startDestinationId = startDestinationId;
	}

	public Long getEndDestinationId() {
		return endDestinationId;
	}

	public void setEndDestinationId(Long endDestinationId) {
		this.endDestinationId = endDestinationId;
	}

}