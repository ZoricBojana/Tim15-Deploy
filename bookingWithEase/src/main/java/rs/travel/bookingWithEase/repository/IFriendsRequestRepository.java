package rs.travel.bookingWithEase.repository;

import java.util.HashSet;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.travel.bookingWithEase.model.FriendRequest;

public interface IFriendsRequestRepository extends JpaRepository<FriendRequest, Long>{

	HashSet<FriendRequest> findByRecieverId(Long recieverId);

	HashSet<FriendRequest> findBySenderId(Long senderId);

}
