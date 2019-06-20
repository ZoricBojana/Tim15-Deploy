package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.Company;

public interface ICompanyRepository extends JpaRepository<Company, Long>{

}
