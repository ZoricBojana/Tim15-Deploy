package rs.travel.bookingWithEase.dto;

public class AccountDTO {

	private String email;
	private String password;
	private String confPassword;
	private String username;
	private String name;
	private String lastName;
	private String city;
	private String phoneNumber;

	public AccountDTO() {
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getConfPassword() {
		return confPassword;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getCity() {
		return city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
