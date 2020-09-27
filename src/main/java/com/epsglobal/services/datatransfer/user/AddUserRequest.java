package com.epsglobal.services.datatransfer.user;

import lombok.Data;

@Data
public class AddUserRequest {
	private String email;
	private String password;
	private Boolean isActive;
}
