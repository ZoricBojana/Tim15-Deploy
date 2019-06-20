package rs.travel.bookingWithEase.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.travel.bookingWithEase.model.FriendRequest;
import rs.travel.bookingWithEase.repository.IFriendsRequestRepository;

@Service
public class FriendsRequestService {

	@Autowired
	private IFriendsRequestRepository friendsRequest;

	public HashSet<FriendRequest> findBySenderId(Long userId) {
		return friendsRequest.findBySenderId(userId);
	}

	public HashSet<FriendRequest> findByRecieverId(Long userId) {
		return friendsRequest.findByRecieverId(userId);
	}

}
