package com.epsglobal.services.datatransfer.manufacter;

import com.epsglobal.services.domain.Manufacter;
import com.fasterxml.jackson.annotation.JsonView;

public class GetManufacterResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	public GetManufacterResponse(Manufacter manufacter) {
		id = manufacter.getId();
		name = manufacter.getName();
	}
}
