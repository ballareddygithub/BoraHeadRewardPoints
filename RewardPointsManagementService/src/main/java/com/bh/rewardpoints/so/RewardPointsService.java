package com.bh.rewardpoints.so;

import java.util.List;

import com.bh.rewardpoints.model.User;

public interface RewardPointsService {

	public List<User> getAllUsers();	
	public User findUserByUserId(String userId);
}
