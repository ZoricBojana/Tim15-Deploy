package rs.travel.bookingWithEase.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"roomId", "day"})})
public class RoomReservationDate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "roomId", nullable = false)
	private Long roomId;
	
	@Column(name = "day", nullable = false)
	private Date day;

	public RoomReservationDate() {
		super();
	}

	public RoomReservationDate(Long id, Long roomId, Date day) {
		super();
		this.id = id;
		this.roomId = roomId;
		this.day = day;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

}
