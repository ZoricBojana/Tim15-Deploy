package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.travel.bookingWithEase.model.Discount;

@Repository
public interface IDiscountRepository  extends JpaRepository<Discount, Long>{

}
