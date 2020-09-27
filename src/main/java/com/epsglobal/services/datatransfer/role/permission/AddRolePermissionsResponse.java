package com.epsglobal.services.datatransfer.role.permission;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class AddRolePermissionsResponse {
	@JsonView
	private List<AddRolePermissionResponse> rolePermissions;
	
	public AddRolePermissionsResponse() {
		rolePermissions = new ArrayList<AddRolePermissionResponse>();
	}
}
