package rs.travel.bookingWithEase.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import rs.travel.bookingWithEase.serializer.FlightSerializer;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FlightReservation {

	/*
	 * Be careful with reservation cancel. CascadeType for passengers, owner and
	 * flight is set to ALL. If reservation is deleted after cancel then change
	 * cascade type.
	 */

	/*
	 * @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) private
	 * Set<Passenger> passengers = new HashSet<>();
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "fr_gen")
	@SequenceGenerator(name = "fr_gen", sequenceName = "FR_SEQ" )
	private Long id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<FlightInvite> invites = new HashSet<>();

  
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JsonSerialize(using = FlightSerializer.class)
	private Flight flight;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	protected RegisteredUser fUser;

	@Column(name = "checkInDate")
	protected Date checkInDate;

	@Column(name = "checkOutDate")
	protected Date checkOutDate;

	@Column(name = "totalPrice")
	protected double totalPrice;

	public FlightReservation(Long id, Set<FlightInvite> invites, Flight flight, RegisteredUser fUser, Date checkInDate,
			Date checkOutDate, double totalPrice) {
		super();
		this.id = id;
		this.invites = invites;
		this.flight = flight;
		this.fUser = fUser;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "FlightReservation [id=" + id + ", invites=" + invites + ", flight=" + flight + ", fUser=" + fUser
				+ ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", totalPrice=" + totalPrice
				+ "]";
	}

	public FlightReservation() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<FlightInvite> getInvites() {
		return invites;
	}

	public void setInvites(Set<FlightInvite> invites) {
		this.invites = invites;
	}

	@JsonIgnore
	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public RegisteredUser getFUser() {
		return fUser;
	}

	public void setFUser(RegisteredUser fUser) {
		this.fUser = fUser;
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}