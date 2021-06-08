package com.bridgelabz.userregistrationproblem.dto;

import java.io.File;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

public @Data class UserDTO {
	
	@NotBlank(message = "First name cannot be blank")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be blank")
	private String lastName;
	
	@JsonFormat(pattern = "dd MMM yyyy")
	private String dob;
	
	@NotBlank(message = "Email id cannot be blank")
	private String emailId;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
	
	@NotBlank(message = "Phone number cannot be blank")
	private String phoneno;
	
	private File profilePic;
}
