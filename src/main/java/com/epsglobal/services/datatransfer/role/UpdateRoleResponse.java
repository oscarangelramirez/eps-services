package com.epsglobal.services.datatransfer.role;

import com.epsglobal.services.domain.Role;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateRoleResponse {
	@JsonView
	private Long id;
	
	public UpdateRoleResponse(Role rol) {
		id = rol.getId();
	}
}
