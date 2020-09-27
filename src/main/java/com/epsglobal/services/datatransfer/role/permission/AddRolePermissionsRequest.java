package com.epsglobal.services.datatransfer.role.permission;

import java.util.List;

import lombok.Data;

@Data
public class AddRolePermissionsRequest {
	private List<AddRolePermissionRequest> rolePermissions;
}
