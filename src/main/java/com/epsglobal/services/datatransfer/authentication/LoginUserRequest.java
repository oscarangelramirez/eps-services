package com.epsglobal.services.datatransfer.authentication;

import lombok.Data;

@Data
public class LoginUserRequest {
	private String email;
	private String password;
}
