package com.epsglobal.services.datatransfer.permission;

import com.epsglobal.services.domain.Permission;
import com.fasterxml.jackson.annotation.JsonView;

public class GetPermissionResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	@JsonView
	private String description;
	
	@JsonView
	private String module;
	
	@JsonView
	private String screen;
	
	public GetPermissionResponse(Permission permission) {
		id = permission.getId();
		name = permission.getName();
		description = permission.getDescription();
		module = permission.getModule();
		screen = permission.getScreen();
	}
}
