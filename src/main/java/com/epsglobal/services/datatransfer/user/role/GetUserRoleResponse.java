package com.epsglobal.services.datatransfer.user.role;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.epsglobal.services.datatransfer.role.GetRoleResponse;
import com.epsglobal.services.datatransfer.role.permission.GetRolePermissionResponse;
import com.epsglobal.services.domain.RolePermission;
import com.epsglobal.services.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonView;

public class GetUserRoleResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private GetRoleResponse role;
	
	@JsonView
	private List<GetRolePermissionResponse> rolePermissions;
	
	public GetUserRoleResponse(UserRole userRole) {
		id = userRole.getId();
		
		role = new GetRoleResponse(userRole.getRole());
		rolePermissions = userRole.getRole().getRolePermissions()
				.stream()
				.sorted(Comparator.comparing(RolePermission::getId))
				.map(rolePermission -> new GetRolePermissionResponse(rolePermission))
				.collect(Collectors.toList());
	}
}
