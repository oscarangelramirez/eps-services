package com.epsglobal.services.datatransfer.location;

import com.epsglobal.services.domain.Location;
import com.fasterxml.jackson.annotation.JsonView;

public class AddLocationResponse {
	@JsonView
	private Long id;
	
	public AddLocationResponse(Location branch) {
		id = branch.getId();
	}
}
