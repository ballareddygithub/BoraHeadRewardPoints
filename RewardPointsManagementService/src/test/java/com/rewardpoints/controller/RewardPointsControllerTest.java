package com.rewardpoints.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bh.rewardpoints.controller.RewardPointsController;
import com.bh.rewardpoints.exception.UserNotFoundException;
import com.bh.rewardpoints.model.User;
import com.bh.rewardpoints.service.RewardPointsService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RewardPointsController.class)
public class RewardPointsControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private RewardPointsController rewardPointsController;

	@MockBean
	private RewardPointsService rewardPointsService;

	@Before
	public void setup() {	
		rewardPointsController = new RewardPointsController();
		this.mockMvc = MockMvcBuilders.standaloneSetup(rewardPointsController).build();
		ReflectionTestUtils.setField(rewardPointsController, "rewardPointsService", rewardPointsService);
	}

	@Test
	public void getAllUsersTest() throws Exception {

		List<User> usersList = new ArrayList<>(); 
		User user = new User();
		user.setEmail("IL10@infolab.com");
		user.setBhEntity("BHEntity1");
		user.setBalance(12L);
		usersList.add(user);
		Mockito.when(rewardPointsService.getAllUsers()).thenReturn(usersList);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/rewardpoints/users")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("[*].email").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("[*].balance").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("[*].bhEntity").exists());
	}
	@Test
	public void getAllUsersTestForNotFound() throws Exception {
		try {
		List<User> usersList = new ArrayList<>(); 
		Mockito.when(rewardPointsService.getAllUsers()).thenReturn(usersList);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/rewardpoints/users")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print());
		}catch(Exception e) {
			assertTrue(e.getCause() instanceof UserNotFoundException);
			assertEquals("No Users Found in database", e.getCause().getMessage());
		}
		
	}
	
	@Test
	public void getUserTest() throws Exception {
		
		User user = new User();
		user.setEmail("IL10@infolab.com");
		user.setBhEntity("BHEntity1");
		user.setBalance(12L);
		Optional<User> userOptional = Optional.of(user);
		Mockito.when(rewardPointsService.findUserByUserId(Mockito.anyString())).thenReturn(userOptional);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/rewardpoints/users/IL10")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("email").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("balance").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("bhEntity").exists());
	}
	@Test
	public void getUserTestForException() throws Exception {

		Optional<User> userOptional = Optional.empty();
		Mockito.when(rewardPointsService.findUserByUserId(Mockito.anyString())).thenReturn(userOptional);
		try {
			mockMvc.perform(MockMvcRequestBuilders
					.get("/rewardpoints/users/IL10")
					.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound());
		}catch(Exception e) {
			assertTrue(e.getCause() instanceof UserNotFoundException);
			assertEquals("User for userId IL10 not found", e.getCause().getMessage());
		}
	}
	
	@Test
	public void withdrawPointsTest() throws Exception {
		
		User user = new User();
		user.setEmail("IL10@infolab.com"); 
		user.setBhEntity("BHEntity1");
		user.setBalance(12L);		
		Mockito.when(rewardPointsService.withdrawalPoints(Mockito.anyString(), Mockito.anyLong())).thenReturn(user);  
		mockMvc.perform(MockMvcRequestBuilders
				.post("/rewardpoints/points/IL10/20")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("email").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("balance").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("bhEntity").exists());
	}
	@Test
	public void withdrawPointsTestForException() throws Exception {

		Mockito.when(rewardPointsService.withdrawalPoints(Mockito.anyString(), Mockito.anyLong())).thenReturn(null);  
		try {
			mockMvc.perform(MockMvcRequestBuilders
					.post("/rewardpoints/points/IL10/20")
					.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound());

		}catch(Exception e) {
			assertTrue(e.getCause() instanceof UserNotFoundException);
			assertEquals("User for userId IL10 not found", e.getCause().getMessage());
		}
	}

}
