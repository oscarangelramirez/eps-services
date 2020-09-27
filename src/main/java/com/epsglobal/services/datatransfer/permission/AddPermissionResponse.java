package com.epsglobal.services.datatransfer.permission;

import com.epsglobal.services.domain.Permission;
import com.fasterxml.jackson.annotation.JsonView;

public class AddPermissionResponse {
	@JsonView
	private Long id;
	
	public AddPermissionResponse(Permission permission) {
		id = permission.getId();
	}
}
