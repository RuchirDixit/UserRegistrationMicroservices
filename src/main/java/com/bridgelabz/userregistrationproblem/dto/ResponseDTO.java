package com.bridgelabz.userregistrationproblem.dto;

import lombok.Data;

public @Data class ResponseDTO {
	private String message;
	private Object data;
	
	public ResponseDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ResponseDTO(String message, Object data) {
		this.message = message;
		this.data = data;
	}
}
