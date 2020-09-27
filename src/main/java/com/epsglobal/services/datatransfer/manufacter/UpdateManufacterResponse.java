package com.epsglobal.services.datatransfer.manufacter;

import com.epsglobal.services.domain.Manufacter;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateManufacterResponse {
	@JsonView
	private Long id;
	
	public UpdateManufacterResponse(Manufacter manufacter) {
		id = manufacter.getId();
	}
}
