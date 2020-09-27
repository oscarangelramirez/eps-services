package com.epsglobal.services.datatransfer.user;

import java.util.Date;

import com.epsglobal.services.domain.User;
import com.fasterxml.jackson.annotation.JsonView;

public class GetUserResponse {
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
	
	public GetUserResponse(User user) {
		id = user.getId();
		email = user.getEmail();
		lastChangeDate = user.getLastChangeDate();
		numberAttemps = user.getNumberAttemps();
		isLocked = user.getIsLocked();
		isActive = user.getIsActive();
	}
}
