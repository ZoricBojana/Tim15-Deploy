package rs.travel.bookingWithEase.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyDTO {

	private Long id;
	private String name;
	private String address;
	private double latitude;
	private double longitude;
	private String description;
	private String cmpType;
	private double rating;
	private int stars;
	private List<String> admins;

	public CompanyDTO() {
		super();
	}

	public CompanyDTO(Long id, String name, String address, String description, String cmpType, double rating,
			int stars) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.cmpType = cmpType;
		this.rating = rating;
		this.stars = stars;
		this.admins = new ArrayList<>();
	}

	public CompanyDTO(Long id, String name, String address, double latitude, double longitude, String description,
			String cmpType, double rating, int stars, List<String> admins) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.cmpType = cmpType;
		this.rating = rating;
		this.stars = stars;
		this.admins = admins;
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

	public String getCmpType() {
		return cmpType;
	}

	public void setCmpType(String cmpType) {
		this.cmpType = cmpType;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public List<String> getAdmins() {
		return admins;
	}

	public void setAdmins(List<String> admins) {
		this.admins = admins;
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
