package com.epsglobal.services.datatransfer.permission;

import com.epsglobal.services.domain.Permission;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdatePermissionResponse {
	@JsonView
	private Long id;
	
	public UpdatePermissionResponse(Permission permission) {
		id = permission.getId();
	}
}
