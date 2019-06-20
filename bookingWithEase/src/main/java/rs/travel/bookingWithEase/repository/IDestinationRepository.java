package rs.travel.bookingWithEase.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.travel.bookingWithEase.model.Destination;

public interface IDestinationRepository extends JpaRepository<Destination, Long>{

	List<Destination> findByAirlineId(long id);
}
