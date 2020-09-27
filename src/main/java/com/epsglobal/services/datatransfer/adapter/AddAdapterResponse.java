package com.epsglobal.services.datatransfer.adapter;

import com.epsglobal.services.domain.Adapter;
import com.fasterxml.jackson.annotation.JsonView;

public class AddAdapterResponse {
	@JsonView
	private Long id;
	
	public AddAdapterResponse(Adapter adapter) {
		id = adapter.getId();
	}
}
