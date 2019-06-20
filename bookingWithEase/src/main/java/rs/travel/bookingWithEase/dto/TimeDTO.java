package rs.travel.bookingWithEase.dto;

import java.sql.Date;

public class TimeDTO {

	private Date start;
	private Date end;

	public TimeDTO() {
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
