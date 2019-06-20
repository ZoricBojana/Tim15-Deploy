package rs.travel.bookingWithEase.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import rs.travel.bookingWithEase.model.Hotel;
import rs.travel.bookingWithEase.dto.HotelSearchDTO;
import rs.travel.bookingWithEase.model.Admin;
import rs.travel.bookingWithEase.repository.IHotelRateRepository;
import rs.travel.bookingWithEase.repository.IHotelRepository;
import rs.travel.bookingWithEase.repository.IUserRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED,  isolation = Isolation.READ_COMMITTED, readOnly = true)
public class HotelService {

	@Autowired
	private IHotelRepository hotels;

	@Autowired
	private IHotelRateRepository hotelRateRepository;
	
	@Autowired
	private IUserRepository users;

	public Hotel findOne(Long id) {
		Optional<Hotel> hotelOpt = hotels.findById(id);
		if (hotelOpt.isPresent()) {
			Double rating = hotelRateRepository.findAverageByCompany(id);
			hotelOpt.get().setRating(rating);
			return hotelOpt.get();
		}
		return null;
	}

	public List<Hotel> findAll() {
		
		List<Hotel> hs = hotels.findAll();
		
		for (Hotel hotel : hs) {
			Double rating = hotelRateRepository.findAverageByCompany(hotel.getId());
			hotel.setRating(rating);
		}
		
		return hs;
	}

	public Hotel findById(Long id) {

		Optional<Hotel> dbHotel = hotels.findById(id);
		if (dbHotel.isPresent()) {
			Double rating = hotelRateRepository.findAverageByCompany(id);
			dbHotel.get().setRating(rating);
			return dbHotel.get();
		} else {
			return null;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Hotel save(Hotel hotel) {
		for (Admin admin : hotel.getAdmins()) {
			
			admin.setCompany(hotel);
			users.save(admin);
		}
		
		return hotels.save(hotel);
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		hotels.deleteById(id);
	}

	public Collection<Hotel> search(HotelSearchDTO hotelSearchDTO) {

		ArrayList<Hotel> result1;
		ArrayList<Hotel> result2;

		if (hotelSearchDTO.getStars() == 0 && hotelSearchDTO.getRating() == 0)
			result1 = hotels.findByNameAndAddress(hotelSearchDTO.getName(), hotelSearchDTO.getAddress());
		else if (hotelSearchDTO.getRating() == 0)
			result1 = hotels.findByNameAddressStars(hotelSearchDTO.getName(), hotelSearchDTO.getAddress(),
					hotelSearchDTO.getStars());
		else if (hotelSearchDTO.getStars() == 0)
			result1 = hotels.findByNameAddressRating(hotelSearchDTO.getName(), hotelSearchDTO.getAddress(),
					hotelSearchDTO.getRating());
		else 
			result1 = hotels.findByNameAddressStarsRating(hotelSearchDTO.getName(), hotelSearchDTO.getAddress(),
				hotelSearchDTO.getStars(), hotelSearchDTO.getRating());

		if (hotelSearchDTO.getCheckIn() != null && hotelSearchDTO.getCheckOut() != null) {

			result2 = hotels.findByRoomAvailability(hotelSearchDTO.getCheckIn(), hotelSearchDTO.getCheckOut());

			result1.retainAll(result2); // intersection
		}
		
		for (Hotel hotel : result1) {
			Double rating = hotelRateRepository.findAverageByCompany(hotel.getId());
			hotel.setRating(rating);
		}
		
		return result1;

	}


}
