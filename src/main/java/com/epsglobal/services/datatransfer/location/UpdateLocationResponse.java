package com.epsglobal.services.datatransfer.location;

import com.epsglobal.services.domain.Location;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateLocationResponse {
	@JsonView
	private Long id;
	
	public UpdateLocationResponse(Location branch) {
		id = branch.getId();
	}
}
