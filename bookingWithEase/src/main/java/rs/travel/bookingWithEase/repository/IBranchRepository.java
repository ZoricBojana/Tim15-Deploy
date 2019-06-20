package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.Branch;

public interface IBranchRepository extends JpaRepository<Branch, Long> {

}
