package com.epsglobal.services.datatransfer.direct;

import com.epsglobal.services.domain.Direct;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateDirectResponse {
	@JsonView
	private Long id;
	
	public UpdateDirectResponse(Direct direct) {
		id = direct.getId();
	}
}
