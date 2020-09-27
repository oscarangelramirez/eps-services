package com.epsglobal.services.datatransfer.user.role;

import java.util.List;

import lombok.Data;

@Data
public class AddUserRolesRequest {
	private List<AddUserRoleRequest> userRoles;
}
