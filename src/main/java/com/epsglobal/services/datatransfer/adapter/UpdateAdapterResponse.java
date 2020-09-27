package com.epsglobal.services.datatransfer.adapter;

import com.epsglobal.services.domain.Adapter;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateAdapterResponse {
	@JsonView
	private Long id;
	
	public UpdateAdapterResponse(Adapter adapter) {
		id = adapter.getId();
	}
}
