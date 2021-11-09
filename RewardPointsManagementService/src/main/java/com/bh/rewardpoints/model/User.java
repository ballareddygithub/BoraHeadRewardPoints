package com.bh.rewardpoints.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ELEARNING_POINTS")
public class User {
	
	@Id
	@Column(name = "USERID")
	private String uesrId;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "BHENTITY")
	private String bhEntity;
	
	@Column(name = "REDEMMED")
	private Long redemmed;
	
	@Column(name = "BALANCE")
	private Long balance;
	
	@Column(name = "CUMULATIVE")
	private Long cumulative;

	public String getUesrId() {
		return uesrId;
	}

	public void setUesrId(String uesrId) {
		this.uesrId = uesrId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBhEntity() {
		return bhEntity;
	}

	public void setBhEntity(String bhEntity) {
		this.bhEntity = bhEntity;
	}

	public Long getRedemmed() {
		return redemmed;
	}

	public void setRedemmed(Long redemmed) {
		this.redemmed = redemmed;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Long getCumulative() {
		return cumulative;
	}

	public void setCumulative(Long cumulative) {
		this.cumulative = cumulative;
	}
	
}
