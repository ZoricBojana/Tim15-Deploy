package rs.travel.bookingWithEase.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Discounts {
	
	@Id
	private Long id;
	private double price;
	private double points;
	
	@OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Discount> discs = new HashSet<>();
	
	public Discounts() {
		super();
	}

	public Discounts(Long id, double price, double points, Set<Discount> discounts) {
		super();
		this.id = id;
		this.price = price;
		this.points = points;
		this.discs = discounts;
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

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public Set<Discount> getDiscounts() {
		return discs;
	}

	public void setDiscounts(Set<Discount> discounts) {
		this.discs = discounts;
	}
	
	
	
	
}
