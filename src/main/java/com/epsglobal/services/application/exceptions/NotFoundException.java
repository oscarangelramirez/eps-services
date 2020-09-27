package com.epsglobal.services.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Integer code;
	
	public NotFoundException(Integer code, String message) {
        super(message);
        this.code = code;
    }
	
	public Integer getCode() {
		return code;
	}
}
