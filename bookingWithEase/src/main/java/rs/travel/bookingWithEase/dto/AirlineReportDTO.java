package rs.travel.bookingWithEase.dto;

import java.util.List;

public class AirlineReportDTO {

	private Long airlineId;

	private String typeSearch;
	private Integer year;
	private Integer month;

	private List<String> type; // day {1, 2,..30}, week - {1, 2,..}, year {jan, feb,..}
	private List<Integer> saleCount; // day - 30, week - 52, year - 12

	public AirlineReportDTO() {
		
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public String getTypeSearch() {
		return typeSearch;
	}

	public void setTypeSearch(String typeSearch) {
		this.typeSearch = typeSearch;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public List<Integer> getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(List<Integer> saleCount) {
		this.saleCount = saleCount;
	}

}
