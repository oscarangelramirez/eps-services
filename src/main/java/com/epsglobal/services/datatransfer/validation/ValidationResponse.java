package com.epsglobal.services.datatransfer.validation;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class ValidationResponse {
	@JsonView
	private Boolean isValid;
	
	@JsonView
	private String message;
}
