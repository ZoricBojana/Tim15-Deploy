package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.travel.bookingWithEase.model.HotelSpecialOffer;

@Repository
public interface IHotelSpecialOfferRepository extends JpaRepository<HotelSpecialOffer, Long>{

}
