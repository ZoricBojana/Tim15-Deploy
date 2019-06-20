package rs.travel.bookingWithEase.dto;

public class DestinationDTO {
//cao
	private Long id;

	private String name;

	private String address;
	
	private Long airlineId;

	public DestinationDTO(Long id, String name, String address, Long airlineId) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.airlineId = airlineId;
	}
	
	public DestinationDTO() {}

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

	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}
}
