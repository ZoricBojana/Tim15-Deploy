package rs.travel.bookingWithEase.dto;

public class VehicleDTO {
	
	private Long id;
	private String registrationNumber;
	private String type;
	private String gear;
	private String color;
	private Double pricePerDay;
	private Long branchId;
	
	public VehicleDTO() {
	}

	public Long getId() {
		return id;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public String getType() {
		return type;
	}

	public String getGear() {
		return gear;
	}

	public String getColor() {
		return color;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
}
