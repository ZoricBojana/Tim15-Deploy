package rs.travel.bookingWithEase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.dto.AdminUserDTO;
import rs.travel.bookingWithEase.model.ADMIN_TYPE;
import rs.travel.bookingWithEase.model.Admin;
import rs.travel.bookingWithEase.model.Company;
import rs.travel.bookingWithEase.model.User;
import rs.travel.bookingWithEase.repository.IUserRepository;

@Service
public class UserService {

	@Autowired
	private IUserRepository users;
	
	@Autowired
	private CompanyService companyService;

	public User findOne(Long id) {
		Optional<User> user = users.findById(id);
		
		if (user.isPresent()) {
			return user.get();
		}
		
		return null;
	}

	public List<User> findAll() {

		return users.findAll();
	}

	public boolean save(User user) {

		if (users.findByUsername(user.getUsername()) == null) {
			users.save(user);
			return true;
		} else {
			return false;
		}

	}

	
	public User update(User user) {
		return users.save(user);
	}

	public void delete(Long id) {
		users.deleteById(id);
	}

	public User dtoToUser(AdminUserDTO adminUserDto) {
		Company company =  null;
		
		if(adminUserDto.getCompanyId() != null)
			company = companyService.findOne(adminUserDto.getCompanyId());
		
		return new Admin(null, adminUserDto.getUsername(), adminUserDto.getFirstName(), adminUserDto.getLastName(),
				adminUserDto.getEmail(), adminUserDto.getPassword(), "", "", "",
				ADMIN_TYPE.strToAdmin(adminUserDto.getAdminType()), company);
	}

	public User findByUsername(String username) throws UsernameNotFoundException {
		return users.findByUsername(username);
	}

	public User findByEmail(String email) {
		return users.findByEmailIgnoreCase(email);

	}
}
