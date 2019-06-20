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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "dateFligh")
	private Date dateFligh;

	@Column(name = "dateLand")
	private Date dateLand;

	@Column(name = "timeTravel")
	private int timeTravel;

	@Column(name = "lengthTravel")
	private int lengthTravel;
	
	@Column(name = "startD")
	private String startD;
	
	@Column(name = "finalD")
	private String finalD;
	
	
	
	@OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Destination> transitions = new HashSet<>();

	@Column(name = "priceTicket")
	private double priceTicket;
	
	
	@Column(name = "informationLuggage")
	private String informationLuggage;
    
	
	@OneToMany(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY,mappedBy="flight")
	private Set<Destination> destinations=new HashSet<>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	Airline airline;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "start_id")
	@JsonIgnore
	private Destination startDestination;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "final_id")
	@JsonIgnore
	private Destination endDestination;
	@OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<FlightReservation> flightReservations = new HashSet<>();	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDateFligh() {
		return dateFligh;
	}

	public void setDateFligh(Date dateFligh) {
		this.dateFligh = dateFligh;
	}

	public Date getDateLand() {
		return dateLand;
	}

	public void setDateLand(Date dateLand) {
		this.dateLand = dateLand;
	}

	public int getTimeTravel() {
		return timeTravel;
	}

	public void setTimeTravel(int timeTravel) {
		this.timeTravel = timeTravel;
	}

	public int getLengthTravel() {
		return lengthTravel;
	}

	public void setLengthTravel(int lengthTravel) {
		this.lengthTravel = lengthTravel;
	}

	public String getStartD() {
		return startD;
	}

	public void setStartD(String startD) {
		this.startD = startD;
	}

	public String getFinalD() {
		return finalD;
	}

	public void setFinalD(String finalD) {
		this.finalD = finalD;
	}



	public Set<Destination> getTransitions() {
		return transitions;
	}

	public void setTransitions(Set<Destination> transitions) {
		this.transitions = transitions;
	}

	public double getPriceTicket() {
		return priceTicket;
	}

	public void setPriceTicket(double priceTicket) {
		this.priceTicket = priceTicket;
	}

	public String getInformationLuggage() {
		return informationLuggage;
	}

	public void setInformationLuggage(String informationLuggage) {
		this.informationLuggage = informationLuggage;
	}

	public Set<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(Set<Destination> destinations) {
		this.destinations = destinations;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Destination getStartDestination() {
		return startDestination;
	}

	public void setStartDestination(Destination startDestination) {
		this.startDestination = startDestination;
	}

	public Destination getEndDestination() {
		return endDestination;
	}

	public void setEndDestination(Destination endDestination) {
		this.endDestination = endDestination;
	}

	public Flight(Long id, String number, Date dateFligh, Date dateLand, int timeTravel, int lengthTravel,
			String startD, String finalD, Set<Destination> transitions, double priceTicket,
			String informationLuggage, Set<Destination> destinations, Airline airline, Destination startDestination,
			Destination endDestination) {
		super();
		this.id = id;
		this.number = number;
		this.dateFligh = dateFligh;
		this.dateLand = dateLand;
		this.timeTravel = timeTravel;
		this.lengthTravel = lengthTravel;
		this.startD = startD;
		this.finalD = finalD;
		this.transitions = transitions;
		this.priceTicket = priceTicket;
		this.informationLuggage = informationLuggage;
		this.destinations = destinations;
		this.airline = airline;
		this.startDestination = startDestination;
		this.endDestination = endDestination;
	}
	
	public Flight(String number, Date dateFligh, Date dateLand, int timeTravel, int lengthTravel,
			String startD, String finalD, double priceTicket, String informationLuggage, Airline airline, Destination startDestination,
			Destination endDestination) {
		super();
		this.number = number;
		this.dateFligh = dateFligh;
		this.dateLand = dateLand;
		this.timeTravel = timeTravel;
		this.lengthTravel = lengthTravel;
		this.startD = startD;
		this.finalD = finalD;
		this.priceTicket = priceTicket;
		this.informationLuggage = informationLuggage;
		this.airline = airline;
		this.startDestination = startDestination;
		this.endDestination = endDestination;
	}

	public Flight() {
		super();
	}

	





	
	
	
	
}
