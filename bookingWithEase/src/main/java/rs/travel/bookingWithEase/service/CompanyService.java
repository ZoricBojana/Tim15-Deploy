package rs.travel.bookingWithEase.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.travel.bookingWithEase.dto.CompanyDTO;
import rs.travel.bookingWithEase.model.Company;
import rs.travel.bookingWithEase.model.User;
import rs.travel.bookingWithEase.repository.ICompanyRepository;
import rs.travel.bookingWithEase.model.Admin;

@Service
@Transactional(propagation = Propagation.REQUIRED,  isolation = Isolation.READ_COMMITTED, readOnly = true)
public class CompanyService {

	@Autowired
	private UserService userService;
	
	
	@Autowired
	private ICompanyRepository companies;
	
	public Company findOne(Long id) {
		Optional<Company> companyOpt = companies.findById(id);
		
		if (companyOpt.isPresent()) {
			
			return companyOpt.get();
		}
		
		return null;
	}

	public Company dtoToCompany(CompanyDTO companyDto) {
		
		Set<Admin> admins = new HashSet<>();
		if (companyDto.getAdmins() != null) {
			for (String adminUsername : companyDto.getAdmins()) {
				User a = userService.findByUsername(adminUsername);
				Admin admin = (Admin) a;
				if (admin != null) {
					admins.add(admin);
				}
			}
		}
		
		return new Company(companyDto.getId(), companyDto.getName(), companyDto.getAddress(), companyDto.getLatitude(), companyDto.getLongitude(),
				companyDto.getDescription(), admins);

	}

}
