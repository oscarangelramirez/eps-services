package com.epsglobal.services.datatransfer.role.permission;

import lombok.Data;

@Data
public class AddRolePermissionRequest {
	private Long idPermission;
	private Integer type;
}
