package rs.travel.bookingWithEase.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

@Component
@Entity
public class FriendRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	private RegisteredUser sender;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	private RegisteredUser reciever;
	
	@Column(nullable = false)
	private Date dateCreated;
	
	public FriendRequest() {
		super();
	}

	public FriendRequest(RegisteredUser sender, RegisteredUser reciever, Date dateCreated) {
		super();
		this.sender = sender;
		this.reciever = reciever;
		this.dateCreated = dateCreated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegisteredUser getSender() {
		return sender;
	}

	public void setSender(RegisteredUser sender) {
		this.sender = sender;
	}

	public RegisteredUser getReciever() {
		return reciever;
	}

	public void setReciever(RegisteredUser reciever) {
		this.reciever = reciever;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
