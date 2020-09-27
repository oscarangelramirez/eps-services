package com.epsglobal.services.datatransfer.manufacter;

import com.epsglobal.services.domain.Manufacter;
import com.fasterxml.jackson.annotation.JsonView;

public class AddManufacterResponse {
	@JsonView
	private Long id;
	
	public AddManufacterResponse(Manufacter manufacter) {
		 id = manufacter.getId();
	}
}
