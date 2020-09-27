package com.epsglobal.services.datatransfer.authentication;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.epsglobal.services.datatransfer.user.role.GetUserRoleResponse;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonView;

public class LoginUserResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String email;
	
	@JsonView
	private Date lastChangeDate;
	
	@JsonView
	private Integer numberAttemps;
	
	@JsonView
	private Boolean isLocked;
	
	@JsonView
	private Boolean isActive;
	
	@JsonView
	private List<GetUserRoleResponse> userRoles;
	
	public LoginUserResponse(User user) {
		id = user.getId();
		email = user.getEmail();
		lastChangeDate = user.getLastChangeDate();
		numberAttemps = user.getNumberAttemps();
		isLocked = user.getIsLocked();
		isActive = user.getIsActive();
		
		userRoles = user.getUserRoles()
				.stream()
				.sorted(Comparator.comparing(UserRole::getId))
				.map(userRol -> new GetUserRoleResponse(userRol))
				.collect(Collectors.toList());
	}
}
