package com.bridgelabz.userregistrationproblem.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.userregistrationproblem.dto.UserDTO;
import com.bridgelabz.userregistrationproblem.entity.UserEntity;
import com.bridgelabz.userregistrationproblem.util.Response;

public interface IUserRegistrationService {

	// To add new user
	Response addNewUser(@Valid UserDTO dto);

	// To verify user
	Response verifyUser(String token);

	// To get list of all users
	List<UserEntity> getAllUsers(String token);

	// To update user data
	Response updateUser(String token, UserDTO dto);

	// To delete user
	Response deleteUser(String token);

	// To check if user is present in database
	boolean checkUser(String token);

	// To check if email id exists for user
	boolean checkEmailExists(String token, String emailId);

	boolean newPrac(String token);

	
}
