package com.bridgelabz.userregistrationproblem.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.userregistrationproblem.exception.UserRegisterException;
import com.bridgelabz.userregistrationproblem.util.Response;
import com.bridgelabz.userregistrationproblem.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;

import com.bridgelabz.userregistrationproblem.dto.UserDTO;
import com.bridgelabz.userregistrationproblem.entity.UserEntity;
import com.bridgelabz.userregistrationproblem.repository.UserRegistrationRepository;

@Service
@Slf4j
public class UserRegistrationService implements IUserRegistrationService{

	@Autowired
	public UserRegistrationRepository userRegistrationRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	TokenUtil tokenUtil;
	
	@Autowired
	EmailService emailService;
	
	/**
	 * To add new user to database
	 * @param dto: User DTO
	 * @return : Response
	 */
	@Override
	public Response addNewUser(@Valid UserDTO dto) {
		String host = System.getenv("HOST_NAME");
		String port = System.getenv("HOST_PORT");
		Optional<UserEntity> isPresent = userRegistrationRepository.findByEmailId(dto.getEmailId());
		if(isPresent.isPresent()) {
			log.error("User exists already.");
			throw new UserRegisterException(400,"User already exists");
		}else {
			UserEntity userEntity = modelMapper.map(dto,UserEntity.class);
			userRegistrationRepository.save(userEntity);
			String token = tokenUtil.createToken(userEntity.getId());
			try {
				// Send email to user for verification
				emailService.sendmail(dto.getEmailId(),"User Verification","Please click on the below link to verify : \n http://"+host+":"+port+"/user/verify/"+token);
			} catch (MessagingException | IOException e) {
				e.printStackTrace();
			}
			log.debug("User added.");
			return new Response(200, "User registered successfully!", token);
		}
	}

	/**
	 * To verify use
	 * @param token : Jwt to check userid
	 * @return : Response
	 */
	@Override
	public Response verifyUser(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setVerify(true);
			userRegistrationRepository.save(isUserPresent.get());
			log.debug("User: " + isUserPresent.get() + " Verified!");
			return new Response(200, "User verified successfully!", null);
		}
		else {
			log.error("User not found.");
			throw new UserRegisterException(404,"User Not found");
		}
	}

	/**
	 * To get all users
	 * @param token : Jwt to check userid
	 * @return  List<UserEntity>
	 */
	@Override
	public List<UserEntity> getAllUsers(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isContactPresent = userRegistrationRepository.findById(id);
		if(isContactPresent.isPresent()) {
			log.debug("Get all users");
			List<UserEntity> getUsers=userRegistrationRepository.findAll();
			return getUsers;
		}
		else {
			log.error("Token not valid");
			throw new UserRegisterException(400,"Token not valid");
		}
	}

	/**
	 * To update user
	 * @param token : Jwt to check userid, dto : UserDTO
	 * @return Response
	 */
	@Override
	public Response updateUser(String token, UserDTO dto) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setFirstName(dto.getFirstName());
			isUserPresent.get().setLastName(dto.getLastName());
			isUserPresent.get().setDob(dto.getDob());
			isUserPresent.get().setEmailId(dto.getEmailId());
			isUserPresent.get().setPassword(dto.getPassword());
			isUserPresent.get().setPhoneno(dto.getPhoneno());
			isUserPresent.get().setProfilePic(dto.getProfilePic());
			isUserPresent.get().setUpdatedDate(LocalDateTime.now());
			userRegistrationRepository.save(isUserPresent.get());
			log.debug("User updated" + isUserPresent.get());
			return new Response(200, "User updated successfully", null);
		}
		else {
			log.error("User not found.");
			throw new UserRegisterException(404,"user Not found");
		}
	}

	/**
	 * To delete user
	 * @param token : JWT to get userid
	 * @return Response
	 */
	@Override
	public Response deleteUser(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent = userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			userRegistrationRepository.delete(isUserPresent.get());
			log.debug("User deleted!!");
			return new Response(200, "User deleted successfully", null);
		}
		else {
			log.error("User not found");
			throw new UserRegisterException(404,"User not found");
		}
	}

	/**
	 * To check if user exists or not
	 * @param : JWT token to get userid
	 * @return boolean if user exists or not
	 */
	@Override
	public boolean checkUser(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent =  userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean checkEmailExists(String token, String emailId) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent =  userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			Optional<UserEntity> emailExists = userRegistrationRepository.findByEmailId(emailId);
			if(emailExists.isPresent()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	@Override
	public boolean newPrac(String token) {
		long id = tokenUtil.decodeToken(token);
		Optional<UserEntity> isUserPresent =  userRegistrationRepository.findById(id);
		if(isUserPresent.isPresent()) {
			return true;
		}
		else {
			return false;
		}
	}
	

}
