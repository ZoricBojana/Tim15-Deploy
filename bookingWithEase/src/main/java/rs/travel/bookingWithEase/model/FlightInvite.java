package rs.travel.bookingWithEase.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FlightInvite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private FlightReservation reservation;

	@Column(nullable = true)
	private String friendEmail;

	@Column(nullable = false)
	private boolean accepted;

	@Column(nullable = false)
	private Date expirationDate;

	@Column(nullable = false)
	private Long seatId;

	@Column(nullable = true)
	private String passport;

	@Column(nullable = true)
	private String firstname;

	@Column(nullable = true)
	private String lastname;

	public FlightInvite() {
		super();
	}

	public FlightInvite(FlightReservation reservation, String friendEmail, Long seatId, String passport) {
		super();
		this.reservation = reservation;
		this.friendEmail = friendEmail;

		this.seatId = seatId;
		this.passport = passport;

		this.accepted = false;
		this.expirationDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(this.expirationDate);
		c.add(Calendar.DATE, 1);
		this.expirationDate = c.getTime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FlightReservation getReservation() {
		return reservation;
	}

	public void setReservation(FlightReservation reservation) {
		this.reservation = reservation;
	}

	public String getFriendEmail() {
		return friendEmail;
	}

	public void setFriendEmail(String friendEmail) {
		this.friendEmail = friendEmail;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
