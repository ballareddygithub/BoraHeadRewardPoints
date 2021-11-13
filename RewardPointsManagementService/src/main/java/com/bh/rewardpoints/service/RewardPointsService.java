package com.bh.rewardpoints.service;

import java.util.List;

import com.bh.rewardpoints.exception.WithdrawalPointsException;
import com.bh.rewardpoints.exception.UserNotFoundException;
import com.bh.rewardpoints.model.User;

public interface RewardPointsService {

	public List<User> getAllUsers();	
	public User findUserByUserId(String userId);
	public User withdrawalPoints(String userId, Long withdral)throws UserNotFoundException, WithdrawalPointsException;
}
