package rs.travel.bookingWithEase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.travel.bookingWithEase.model.HotelServiceTypePrices;

@Repository
public interface IHotelServiceTypePricesRepository extends JpaRepository<HotelServiceTypePrices, Long> {

}
