package com.bridgelabz.userregistrationproblem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.userregistrationproblem.dto.ResponseDTO;
import com.bridgelabz.userregistrationproblem.dto.UserDTO;
import com.bridgelabz.userregistrationproblem.entity.UserEntity;
import com.bridgelabz.userregistrationproblem.service.IUserRegistrationService;
import com.bridgelabz.userregistrationproblem.util.Response;

import jdk.internal.org.jline.utils.Log;

@RestController
@RequestMapping("/user")
public class UseRegistrationController {

	@Autowired
	public IUserRegistrationService userRegistrationService;
	
	@PostMapping("/newUser")
	public ResponseEntity<Response> addNewUser(@Valid @RequestBody UserDTO dto){
		Response userEntity = userRegistrationService.addNewUser(dto);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> verifyUser(@PathVariable String token){
		Response userEntity = userRegistrationService.verifyUser(token);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	@GetMapping("/checkUser/{token}")
	public ResponseEntity<Boolean> checkUser(@PathVariable String token){
		boolean userPresent = userRegistrationService.checkUser(token);
		return new ResponseEntity<Boolean>(userPresent,HttpStatus.OK);
	}
	
	@GetMapping("/checkEmailExists/{token}/{emailId}")
	public ResponseEntity<Boolean> checkEmailExists(@PathVariable String token,@PathVariable String emailId){
		boolean userPresent = userRegistrationService.checkEmailExists(token,emailId);
		return new ResponseEntity<Boolean>(userPresent,HttpStatus.OK);
	}
	
	@GetMapping("/practice")
	public ResponseEntity<Boolean> practice(@RequestHeader("token") String token){
		boolean isPresent = userRegistrationService.newPrac(token);
		return new ResponseEntity<Boolean>(isPresent,HttpStatus.OK);
		
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<List<?>> getAllUsers(@RequestHeader("token") String token){
		List<UserEntity> userList = userRegistrationService.getAllUsers(token);
		return new ResponseEntity<>(userList,HttpStatus.OK);
	}
	
	@PutMapping("/updateUser/{token}")
	public ResponseEntity<Response> updateUser(@PathVariable String token,@RequestBody UserDTO dto){
		Response userEntity = userRegistrationService.updateUser(token,dto);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteUser/{token}")
	public ResponseEntity<Response> deleteUser(@PathVariable String token){
		Response userEntity = userRegistrationService.deleteUser(token);
		return new ResponseEntity<Response>(userEntity,HttpStatus.OK);
	}
}
