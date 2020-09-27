package com.epsglobal.services.datatransfer.role.permission;

import com.epsglobal.services.domain.RolePermission;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateRolePermissionResponse {
	@JsonView
	private Long id;
	
	public UpdateRolePermissionResponse(RolePermission rolePermission) {
		id = rolePermission.getId();
	}
}
