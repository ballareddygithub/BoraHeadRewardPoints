package com.bh.rewardpoints.dao;

import org.springframework.data.repository.CrudRepository;

import com.bh.rewardpoints.model.User;

public interface RewardPointsRepository extends CrudRepository<User, String>{

}
