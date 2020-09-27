package com.epsglobal.services.datatransfer.user.role;

import com.epsglobal.services.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonView;

public class DeleteUserRoleResponse {
	@JsonView
	private Long id;
	
	public DeleteUserRoleResponse(UserRole userRol) {
		id = userRol.getId();
	}
}
