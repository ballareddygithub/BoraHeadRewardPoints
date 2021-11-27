package com.bh.rewardpoints.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bh.rewardpoints.exception.WithdrawalPointsException;
import com.bh.rewardpoints.exception.UserNotFoundException;
import com.bh.rewardpoints.model.User;

public interface RewardPointsService {

	public List<User> getAllUsers();	
	public Optional<User> findUserByUserId(String userId);
	public User withdrawalPoints(String userId, Long withdral)throws UserNotFoundException, WithdrawalPointsException;
	public Map<String, List<User>> updateUsers(List<User> listUser);
}
