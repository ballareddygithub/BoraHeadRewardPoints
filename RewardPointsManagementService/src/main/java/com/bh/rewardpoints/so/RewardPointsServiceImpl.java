package com.bh.rewardpoints.so;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.rewardpoints.dao.RewardPointsRepository;
import com.bh.rewardpoints.model.User;

@Service
public class RewardPointsServiceImpl implements RewardPointsService{

	@Autowired
	private RewardPointsRepository rewardPointsRepository;
	
	@Override
	public List<User> getAllUsers() {
		List<User> usersList = new ArrayList<>();
		rewardPointsRepository.findAll().forEach(user -> usersList.add(user));
		return usersList; 
	}
	@Override
	public User findUserByUserId(String userId) {		
		return rewardPointsRepository.findById(userId).get();
	}
}
