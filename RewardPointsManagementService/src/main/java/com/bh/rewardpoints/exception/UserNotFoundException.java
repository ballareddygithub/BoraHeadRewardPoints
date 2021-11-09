package com.bh.rewardpoints.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String userId) {
		super(String.format("User for userId %s not found", userId));
	}


}
