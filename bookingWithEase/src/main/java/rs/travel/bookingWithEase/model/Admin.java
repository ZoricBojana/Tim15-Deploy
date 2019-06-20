package rs.travel.bookingWithEase.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Admin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ADMIN_TYPE type;

	@ManyToOne(fetch = FetchType.EAGER)
	private Company company;
	
	private boolean passwordChanged;

	public Admin() {
		super();

	}

	public Admin(Long id, String username, String firstName, String lastName, String email, String password,
			String city, String telephoneNumber, String passportNumber) {
		super(id, username, firstName, lastName, email, password, city, telephoneNumber, passportNumber);
		this.passwordChanged = false;
	}

	public Admin(Long id, String username, String firstName, String lastName, String email, String password,
			String city, String telephoneNumber, String passportNumber, ADMIN_TYPE type, Company company) {
		super(id, username, firstName, lastName, email, password, city, telephoneNumber, passportNumber);
		this.type = type;
		this.company = company;
		this.passwordChanged = false;
		
	}

	public ADMIN_TYPE getType() {
		return type;
	}

	public void setType(ADMIN_TYPE type) {
		this.type = type;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}

}
