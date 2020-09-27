package com.epsglobal.services.datatransfer.role.permission;

import com.epsglobal.services.datatransfer.permission.GetPermissionResponse;
import com.epsglobal.services.domain.RolePermission;
import com.fasterxml.jackson.annotation.JsonView;

public class GetRolePermissionResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Integer type;
	
	@JsonView
	private GetPermissionResponse permission;
	
	public GetRolePermissionResponse(RolePermission rolePermission) {
		id = rolePermission.getId();
		type = rolePermission.getType().ordinal();
		
		permission = new GetPermissionResponse(rolePermission.getPermission());
	}
}
