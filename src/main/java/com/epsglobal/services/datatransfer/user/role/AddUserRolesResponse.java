package com.epsglobal.services.datatransfer.user.role;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class AddUserRolesResponse {
	@JsonView
	private List<AddUserRoleResponse> userRoles;
	
	public AddUserRolesResponse() {
		userRoles = new ArrayList<AddUserRoleResponse>();
	}
}
