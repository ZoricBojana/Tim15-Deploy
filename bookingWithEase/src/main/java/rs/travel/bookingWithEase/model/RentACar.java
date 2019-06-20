package rs.travel.bookingWithEase.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
public class RentACar extends Company {

	private static final long serialVersionUID = 1L;

	// private Location location;

	@OneToMany(mappedBy = "rac", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Branch> branches = new HashSet<>();

	@OneToMany(mappedBy = "racservice", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<RACSpecialOffer> specialOffers = new HashSet<>();
	
	public RentACar() {
	}

	public RentACar(long id, String name, String address, String description, double rating) {
		super(id, name, address, description, rating);

	}

	public RentACar(Company company) {
		this.id = company.id;
		this.name = company.name;
		this.address = company.address;
		this.latitude = company.latitude;
		this.longitude = company.longitude;
		this.description = company.description;
		this.rating = company.rating;
	}

	public Set<Branch> getBranches() {
		return branches;
	}
	
	@JsonIgnore
	public Set<Vehicle> getVehicles() {
		Set<Vehicle> vehs = new HashSet<>();
		
		for (Branch branch : this.branches) {
			for (Vehicle vehicle : branch.getVehicles()) {
				vehs.add(vehicle);
			}
		}
		return vehs;
	}

	public void setBranches(Set<Branch> branches) {
		this.branches = branches;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		RentACar r = (RentACar) o;
		if (r.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, r.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Rent a car [id=" + id + "]";
	}

	public void addBranch(Branch b) {
		this.branches.add(b);
	}

	public Set<RACSpecialOffer> getSpecialOffers() {
		return specialOffers;
	}

	public void setSpecialOffers(Set<RACSpecialOffer> specialOffers) {
		this.specialOffers = specialOffers;
	}
}
