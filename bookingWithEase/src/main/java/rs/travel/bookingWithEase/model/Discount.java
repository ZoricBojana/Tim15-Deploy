package rs.travel.bookingWithEase.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Discount {
	
	@Id
	private Long id;
	private int points;
	private int discount;
	
	public Discount() {
		super();
	}

	public Discount(Long id, int points, int discount) {
		super();
		this.id = id;
		this.points = points;
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	public int getDiscount() {
		return discount;
	}


	public void setDiscount(int discount) {
		this.discount = discount;
	}

	
	
	
}
