package com.bh.rewardpoints.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bh.rewardpoints.dao.RewardPointsRepository;
import com.bh.rewardpoints.exception.UserNotFoundException;
import com.bh.rewardpoints.exception.WithdrawalPointsException;
import com.bh.rewardpoints.model.User;

@Named
public class RewardPointsServiceImpl implements RewardPointsService{

	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Inject
	private RewardPointsRepository rewardPointsRepository;

	@Override
	public List<User> getAllUsers() {
		List<User> usersList = new ArrayList<>();
		rewardPointsRepository.findAll().forEach(user -> usersList.add(user));
		logger.info("Total number of Users available in database : {}", usersList.size());
		return usersList; 
	}
	@Override
	public Optional<User> findUserByUserId(String userId) {	
		logger.info("find User By Id {} : ", userId);
		return rewardPointsRepository.findById(userId);
	}

	@Override
	public User withdrawalPoints(String userId, Long withdrawal) throws UserNotFoundException, WithdrawalPointsException {

		logger.info("User : {} withdrwal Points {}", userId, withdrawal);
    	Optional<User> userOptional = rewardPointsRepository.findById(userId);
		User updatedUser = null;
		if(!userOptional.isPresent()) {
			logger.info("User is not availale for the userid : {}", userId);
			throw new UserNotFoundException(String.format("User is not availale for the userid : %s", userId));
		}else {
			User user = userOptional.get();
			if(withdrawal > user.getBalance()) {
				logger.info("InSufficiant Points, available balance is : {}", user.getBalance());
				throw new WithdrawalPointsException(String.format("InSufficiant Points, available balance is : %s", user.getBalance()));
			}else if(withdrawal < 0) {
				logger.info("You {} can't withdraw negetive points {} ", userId, withdrawal);
				throw new WithdrawalPointsException(String.format("You can't withdraw negetive points %s ", withdrawal));
			}else {
				logger.info("Withdrawal Points : {}", withdrawal);
				Long remainingBalance  = user.getBalance() - withdrawal;
				logger.info("Remaining Balance : {}", remainingBalance);
				user.setBalance(remainingBalance);
				Long redemed  = user.getRedeemed() + withdrawal;
				logger.info("Redemed Points : {}", redemed);
				user.setRedeemed(redemed);
				updatedUser = rewardPointsRepository.save(user);
				logger.info("Successfully withdrawn reward points");
				logger.info("Updated User : {}", updatedUser);
				
			}
		}

		return updatedUser;
	}
	
	@Override
	public Map<String, List<User>> updateUsers(List<User> listUser) {
		Map<String, List<User>> allUsers = new HashMap<>(); 
		List<User> existingUsersupdatedFromIspring = new ArrayList<>();
		List<User> newUsersCreatedInDBFromISpring = new ArrayList<>();
		listUser.forEach(user -> {
			Optional<User> userOptional = rewardPointsRepository.findById(user.getUesrId());
			if(userOptional.isPresent()) {
				// update User if user present in DB.
				User userFromDB = userOptional.get();
				User newUser = new User();
				newUser.setUesrId(userFromDB.getUesrId());
				newUser.setBalance(user.getBalance() + userFromDB.getBalance());
				newUser.setBhEntity(user.getBhEntity());
				newUser.setCumulative(user.getCumulative() + userFromDB.getCumulative());
				newUser.setEmail(user.getEmail());
				newUser.setRedeemed(user.getRedeemed() + userFromDB.getRedeemed());
				existingUsersupdatedFromIspring.add(rewardPointsRepository.save(newUser));
			}else {
				// create/save User which is from ISpring not present in DB.
				newUsersCreatedInDBFromISpring.add(rewardPointsRepository.save(user));
			}
		});
		allUsers.put("NewUsersCreated", newUsersCreatedInDBFromISpring);
		allUsers.put("ExistingUserUpdated", existingUsersupdatedFromIspring);
		return allUsers;
	}

}
