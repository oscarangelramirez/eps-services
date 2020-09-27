package com.epsglobal.services.datatransfer.role.permission;

import com.epsglobal.services.domain.RolePermission;
import com.fasterxml.jackson.annotation.JsonView;

public class AddRolePermissionResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Long idPermission;
	
	public AddRolePermissionResponse(RolePermission rolePermission) {
		id = rolePermission.getId();
		idPermission = rolePermission.getPermission().getId();
	}
}
