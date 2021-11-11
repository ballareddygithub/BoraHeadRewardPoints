package com.bh.rewardpoints.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bh.rewardpoints.exception.UserNotFoundException;
import com.bh.rewardpoints.exception.WithdrawalPointsException;
import com.bh.rewardpoints.model.User;
import com.bh.rewardpoints.so.RewardPointsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="User Points API", description = "Api for ISpring")
@RequestMapping("/rewardpoints")
@CrossOrigin
public class RewardPointsController {
	
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    @Inject
    private RewardPointsService rewardPointsService;
    
    @Operation(summary="getAllUsers",description="API to fetch all user info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all ok"),
            @ApiResponse(responseCode = "400", description = "bad request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {   
    	List<User> usersList = rewardPointsService.getAllUsers();
    	logger.info("All Users :{} ", usersList);
        return ResponseEntity.ok(usersList);
    }
    @Operation(summary="getUserbyUserId",description="API to fetch user by User Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all ok"),
            @ApiResponse(responseCode = "400", description = "bad request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserbyUserId(@PathVariable("id") String userId) throws UserNotFoundException{   
    	User user = rewardPointsService.findUserByUserId(userId);
    	if(user == null) {
    		throw new UserNotFoundException(userId);
    	}
    	logger.info("User {} for an id : {}", user, userId);
        return ResponseEntity.ok(user);
    }  
    @Operation(summary="withdrawPoints",description="API to withdraw points from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all ok"),
            @ApiResponse(responseCode = "400", description = "bad request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Permission denied")
    })
    @PostMapping("/points/{id}/{withdrawal}")
    public ResponseEntity<?> withdrawPoints(@PathVariable("id") String userId, @PathVariable("withdrawal") String withdrawal) throws UserNotFoundException, NumberFormatException, WithdrawalPointsException{   
    	User user = rewardPointsService.withdrawalPoints(userId, Long.valueOf(withdrawal));   
    	logger.info("User : {}", user);
        return ResponseEntity.ok(user);
    } 
}
