package com.epsglobal.services.datatransfer.user.role;

import java.util.List;

import lombok.Data;

@Data
public class DeleteUserRolesRequest {
	private List<DeleteUserRoleRequest> userRoles;
}
