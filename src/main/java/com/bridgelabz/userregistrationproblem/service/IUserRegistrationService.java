package com.bridgelabz.userregistrationproblem.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.userregistrationproblem.dto.UserDTO;
import com.bridgelabz.userregistrationproblem.entity.UserEntity;
import com.bridgelabz.userregistrationproblem.util.Response;

public interface IUserRegistrationService {

	Response addNewUser(@Valid UserDTO dto);

	Response verifyUser(String token);

	List<UserEntity> getAllUsers(String token);

	Response updateUser(String token, UserDTO dto);

	Response deleteUser(String token);

	
}
