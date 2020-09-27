package com.epsglobal.services.datatransfer.user;

import com.epsglobal.services.domain.User;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateUserResponse {
	@JsonView
	private Long id;
	
	public UpdateUserResponse(User user) {
		id = user.getId();
	}
}
