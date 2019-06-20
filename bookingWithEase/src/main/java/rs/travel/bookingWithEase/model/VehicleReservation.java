package rs.travel.bookingWithEase.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import rs.travel.bookingWithEase.serializer.VehicleSerializer;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class VehicleReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "vr_gen")
	@SequenceGenerator(name = "vr_gen", sequenceName = "VR_SEQ" )
	protected Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonSerialize(using = VehicleSerializer.class)
	protected Vehicle vehicle;

	@ManyToOne
	protected RegisteredUser vehicle_user;

	protected Date checkInDate;
	protected Date checkOutDate;
	
	@ManyToMany(fetch = FetchType.EAGER)
	protected  Set<RACSpecialOffer> racSpecialOffers = new HashSet<>();

	
	protected Double totalPrice;

	@OneToOne(mappedBy="reservation")
	private VehicleRate rate;
	
	@Version
	protected Long version;
	
	public VehicleReservation() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public RegisteredUser getUser() {
		return vehicle_user;
	}

	public void setUser(RegisteredUser vehicle_user) {
		this.vehicle_user = vehicle_user;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	@JsonIgnore
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@JsonIgnore
	public RegisteredUser getVehicle_user() {
		return vehicle_user;
	}

	public Set<RACSpecialOffer> getRacSpecialOffers() {
		return racSpecialOffers;
	}

	public void setVehicle_user(RegisteredUser vehicle_user) {
		this.vehicle_user = vehicle_user;
	}

	public void setRacSpecialOffers(Set<RACSpecialOffer> racSpecialOffers) {
		this.racSpecialOffers = racSpecialOffers;
	}

	public VehicleRate getRate() {
		return rate;
	}

	public void setRate(VehicleRate rate) {
		this.rate = rate;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
