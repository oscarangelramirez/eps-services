package com.epsglobal.services.datatransfer.permission;

import lombok.Data;

@Data
public class AddPermissionRequest {
	private String name;
	private String description;
	private String module;
	private String screen;
}
