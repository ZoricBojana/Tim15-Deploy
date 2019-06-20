package rs.travel.bookingWithEase.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class HotelServiceTypePrices implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private boolean bedAndBreakfastEnabled = false;
	private double bedAndBreakfastPrice;
	
	private boolean halfBoardEnabled = false;
	private double halfBoardPrice;
	
	private boolean fullBoardEnabled = false;
	private double fullBoardPrice;
	
	private boolean allInclusiveEnabled = false;
	private double allInclusivePrice;
	
	public HotelServiceTypePrices() {
		super();
	}

	public HotelServiceTypePrices(Long id, boolean bedAndBreakfastEnabled, double bedAndBreakfastPrice,
			boolean halfBoardEnabled, double halfBoardPrice, boolean fullBoardEnabled, double fullBoardPrice,
			boolean allInclusiveEnabled, double allInclusivePrice) {
		super();
		this.id = id;
		this.bedAndBreakfastEnabled = bedAndBreakfastEnabled;
		this.bedAndBreakfastPrice = bedAndBreakfastPrice;
		this.halfBoardEnabled = halfBoardEnabled;
		this.halfBoardPrice = halfBoardPrice;
		this.fullBoardEnabled = fullBoardEnabled;
		this.fullBoardPrice = fullBoardPrice;
		this.allInclusiveEnabled = allInclusiveEnabled;
		this.allInclusivePrice = allInclusivePrice;
	}
	
	@Override
	public String toString() {
		return "HotelServiceTypePrices [id=" + id + ", bedAndBreakfastEnabled=" + bedAndBreakfastEnabled
				+ ", bedAndBreakfastPrice=" + bedAndBreakfastPrice + ", halfBoardEnabled=" + halfBoardEnabled
				+ ", halfBoardPrice=" + halfBoardPrice + ", fullBoardEnabled=" + fullBoardEnabled + ", fullBoardPrice="
				+ fullBoardPrice + ", allInclusiveEnabled=" + allInclusiveEnabled + ", allInclusivePrice="
				+ allInclusivePrice + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isBedAndBreakfastEnabled() {
		return bedAndBreakfastEnabled;
	}

	public void setBedAndBreakfastEnabled(boolean bedAndBreakfastEnabled) {
		this.bedAndBreakfastEnabled = bedAndBreakfastEnabled;
	}

	public double getBedAndBreakfastPrice() {
		return bedAndBreakfastPrice;
	}

	public void setBedAndBreakfastPrice(double bedAndBreakfastPrice) {
		this.bedAndBreakfastPrice = bedAndBreakfastPrice;
	}

	public boolean isHalfBoardEnabled() {
		return halfBoardEnabled;
	}

	public void setHalfBoardEnabled(boolean halfBoardEnabled) {
		this.halfBoardEnabled = halfBoardEnabled;
	}

	public double getHalfBoardPrice() {
		return halfBoardPrice;
	}

	public void setHalfBoardPrice(double halfBoardPrice) {
		this.halfBoardPrice = halfBoardPrice;
	}

	public boolean isFullBoardEnabled() {
		return fullBoardEnabled;
	}

	public void setFullBoardEnabled(boolean fullBoardEnabled) {
		this.fullBoardEnabled = fullBoardEnabled;
	}

	public double getFullBoardPrice() {
		return fullBoardPrice;
	}

	public void setFullBoardPrice(double fullBoardPrice) {
		this.fullBoardPrice = fullBoardPrice;
	}

	public boolean isAllInclusiveEnabled() {
		return allInclusiveEnabled;
	}

	public void setAllInclusiveEnabled(boolean allInclusiveEnabled) {
		this.allInclusiveEnabled = allInclusiveEnabled;
	}

	public double getAllInclusivePrice() {
		return allInclusivePrice;
	}

	public void setAllInclusivePrice(double allInclusivePrice) {
		this.allInclusivePrice = allInclusivePrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
