package com.epsglobal.services.datatransfer.role;

import com.epsglobal.services.domain.Role;
import com.fasterxml.jackson.annotation.JsonView;

public class AddRoleResponse {
	@JsonView
	private Long id;
	
	public AddRoleResponse(Role rol) {
		id = rol.getId();
	}
}
