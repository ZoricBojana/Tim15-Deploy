package rs.travel.bookingWithEase.dto;

public class BookQrrDTO {
	
	private Long id;
	private Long version;
	
	public BookQrrDTO() {
		super();
	}

	public BookQrrDTO(Long id, Long version) {
		super();
		this.id = id;
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	
}
