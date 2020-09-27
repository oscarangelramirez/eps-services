package com.epsglobal.services.datatransfer.role;

import com.epsglobal.services.domain.Role;
import com.fasterxml.jackson.annotation.JsonView;

public class GetRoleResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	@JsonView
	private String description;
	
	public GetRoleResponse(Role rol) {
		id = rol.getId();
		name = rol.getName();
		description = rol.getDescription();
	}
}
