package com.epsglobal.services.datatransfer.location;

import com.epsglobal.services.domain.Location;
import com.fasterxml.jackson.annotation.JsonView;

public class GetLocationResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	public GetLocationResponse(Location branch) {
		id = branch.getId();
		name = branch.getName();
	}
}
