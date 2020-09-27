package com.epsglobal.services.datatransfer.user;

import com.epsglobal.services.domain.User;
import com.fasterxml.jackson.annotation.JsonView;

public class AddUserResponse {
	@JsonView
	private Long id;
	
	public AddUserResponse(User user) {
		id = user.getId();
	}
}
