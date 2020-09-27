package com.epsglobal.services.datatransfer.user.role;

import com.epsglobal.services.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonView;

public class AddUserRoleResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Long idRole;
	
	public AddUserRoleResponse(UserRole userRole) {
		id = userRole.getId();
		idRole = userRole.getRole().getId();
	}
}
