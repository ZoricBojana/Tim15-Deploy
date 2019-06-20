package rs.travel.bookingWithEase.dto;

import rs.travel.bookingWithEase.model.Airline;

public class AirlineDTO {
	private Long id;
	private String name;
	private String address;
	private double latitude;
	private double longitude;
	private String description;

	public AirlineDTO() {
		super();
	}
	
	public AirlineDTO(Long id, String name, String address, String description) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
	}
	
	public AirlineDTO(Airline airline) {
		this.id = airline.getId();
		this.name = airline.getName();
		this.address = airline.getAddress();
		this.description = airline.getDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}