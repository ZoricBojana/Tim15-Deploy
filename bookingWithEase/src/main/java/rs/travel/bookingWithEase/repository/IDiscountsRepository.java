package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.travel.bookingWithEase.model.Discounts;

@Repository
public interface IDiscountsRepository extends JpaRepository<Discounts, Long>{

}
