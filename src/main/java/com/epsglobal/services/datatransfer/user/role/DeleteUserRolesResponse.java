package com.epsglobal.services.datatransfer.user.role;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class DeleteUserRolesResponse {
	@JsonView
	private List<DeleteUserRoleResponse> userRoles;
	
	public DeleteUserRolesResponse() {
		userRoles = new ArrayList<DeleteUserRoleResponse>();
	}
}
