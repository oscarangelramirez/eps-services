package com.epsglobal.services.datatransfer.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
	private String email;
	private Boolean isLocked;
	private Boolean isActive;
}
