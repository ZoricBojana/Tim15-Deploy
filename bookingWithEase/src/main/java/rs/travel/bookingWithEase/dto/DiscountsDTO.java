package rs.travel.bookingWithEase.dto;

import java.util.Set;

import rs.travel.bookingWithEase.model.Discount;

public class DiscountsDTO {
	
	private Long id;
	private double price;
	private int points;
	private Set<Discount> discounts;
	
	public DiscountsDTO() {
		super();
	}

	public DiscountsDTO(Long id, double price, int points, Set<Discount> discounts) {
		super();
		this.id = id;
		this.price = price;
		this.points = points;
		this.discounts = discounts;
	}
	
	@Override
	public String toString() {
		return "DiscountsDTO [id=" + id + ", price=" + price + ", points=" + points + ", discounts=" + discounts + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Set<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}
	
	

	
}
