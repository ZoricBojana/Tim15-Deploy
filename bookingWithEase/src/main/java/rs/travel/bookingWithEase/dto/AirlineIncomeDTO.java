package rs.travel.bookingWithEase.dto;

import java.util.Date;

public class AirlineIncomeDTO {

	private Long airlineId;
	private Date start;
	private Date end;

	public AirlineIncomeDTO(Long airlineId, Date start, Date end) {
		super();
		this.airlineId = airlineId;
		this.start = start;
		this.end = end;
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
