package com.rewardpoints.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.bh.rewardpoints.dao.RewardPointsRepository;
import com.bh.rewardpoints.exception.UserNotFoundException;
import com.bh.rewardpoints.exception.WithdrawalPointsException;
import com.bh.rewardpoints.model.User;
import com.bh.rewardpoints.service.RewardPointsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class RewardPointsServiceImplTest {

	@InjectMocks
	private RewardPointsServiceImpl rewardPointsService;

	@Mock
	private RewardPointsRepository rewardPointsRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAllUsersTest() {

		List<User> usersList = new ArrayList<>(); 
		User user = new User();
		user.setUesrId("IL10");
		user.setEmail("IL10@infolab.com");
		user.setBhEntity("BHEntity1");
		user.setBalance(120L);
		usersList.add(user);		
		User secondUser = new User();
		secondUser.setEmail("IL11@infolab.com");
		secondUser.setBhEntity("BHEntity2");
		secondUser.setBalance(200L);
		usersList.add(secondUser);		
		Mockito.when(rewardPointsRepository.findAll()).thenReturn(usersList);
		List<User> usersListResp = rewardPointsService.getAllUsers();
		Assert.assertNotNull(usersListResp);
		Assert.assertEquals(usersList.get(0).getBhEntity(), usersListResp.get(0).getBhEntity());
		Assert.assertEquals(usersList.get(0).getBalance(), usersListResp.get(0).getBalance());
		Assert.assertEquals(usersList.get(0).getEmail(), usersListResp.get(0).getEmail());
		Assert.assertEquals(usersList.get(1).getBhEntity(), usersListResp.get(1).getBhEntity());
		Assert.assertEquals(usersList.get(1).getBalance(), usersListResp.get(1).getBalance());
		Assert.assertEquals(usersList.get(1).getEmail(), usersListResp.get(1).getEmail());
	}

	@Test
	public void findUserByUserIdTest() {
		User user = new User();
		user.setUesrId("IL30");
		user.setEmail("IL10@infolab.com");
		user.setBhEntity("BHEntity1");
		user.setBalance(120L);
		Optional<User> optional = Optional.of(user);
		Mockito.when(rewardPointsRepository.findById(Mockito.anyString())).thenReturn(optional);
		Optional<User> optionalResp = rewardPointsService.findUserByUserId(user.getUesrId());
		Assert.assertNotNull(optionalResp);
		Assert.assertEquals(optional.get(), optionalResp.get());
		Assert.assertEquals(optional.get().getBhEntity(), optionalResp.get().getBhEntity());
		Assert.assertEquals(optional.get().getBalance(), optionalResp.get().getBalance());
		Assert.assertEquals(optional.get().getEmail(), optionalResp.get().getEmail());
	}
	
	@Test
	public void withdrawalPointsTest() throws UserNotFoundException, WithdrawalPointsException {
		User user = new User();
		user.setCumulative(300L);
		user.setEmail("IL10@infolab.com");
		user.setBhEntity("BHEntity1");
		user.setBalance(120L);
		user.setRedeemed(10L);
		Optional<User> optional = Optional.of(user);
		Mockito.when(rewardPointsRepository.findById(Mockito.anyString())).thenReturn(optional);
		User updatedUser = new User();
		updatedUser.setBalance(user.getBalance() - 10L);
		updatedUser.setRedeemed(user.getRedeemed() + 10L);	
		Mockito.when(rewardPointsRepository.save(user)).thenReturn(updatedUser);
		User userResp = rewardPointsService.withdrawalPoints("IL10", 10L);
		Assert.assertNotNull(userResp);
		Assert.assertNull(userResp.getCumulative());
		Assert.assertEquals(optional.get().getBalance(), userResp.getBalance());
		Assert.assertEquals(optional.get().getRedeemed(), userResp.getRedeemed());
	}
	@Test(expected = UserNotFoundException.class)
	public void withdrawalPointsTestForException() throws UserNotFoundException, WithdrawalPointsException {
		Optional<User> optional = Optional.empty();
		Mockito.when(rewardPointsRepository.findById(Mockito.anyString())).thenReturn(optional);
		rewardPointsService.withdrawalPoints("IL10", 10L);
	}
	@Test(expected = WithdrawalPointsException.class)
	public void withdrawalPointsForLessBalanceTest() throws UserNotFoundException, WithdrawalPointsException {
		User user = new User();
		user.setEmail("IL10@infolab.com");
		user.setBhEntity("BHEntity1");
		user.setBalance(120L);
		user.setRedeemed(10L);
		Optional<User> optional = Optional.of(user);
		Mockito.when(rewardPointsRepository.findById(Mockito.anyString())).thenReturn(optional);
		rewardPointsService.withdrawalPoints("IL10", 200L);
	}
	@Test(expected = WithdrawalPointsException.class)
	public void withdrawalPointsForNegetaveBalanceTest() throws UserNotFoundException, WithdrawalPointsException {
		User user = new User();
		user.setEmail("IL10@infolab.com");
		user.setBhEntity("BHEntity1");
		user.setBalance(120L);
		user.setRedeemed(10L);
		Optional<User> optional = Optional.of(user);
		Mockito.when(rewardPointsRepository.findById(Mockito.anyString())).thenReturn(optional);
		rewardPointsService.withdrawalPoints("IL10", -10L);
	}
}
