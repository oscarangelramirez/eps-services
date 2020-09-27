package com.epsglobal.services.datatransfer.direct;

import com.epsglobal.services.domain.Direct;
import com.fasterxml.jackson.annotation.JsonView;

public class AddDirectResponse {
	@JsonView
	private Long id;
	
	public AddDirectResponse(Direct direct) {
		id = direct.getId();
	}
}
