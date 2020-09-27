package com.epsglobal.services.datatransfer.carrier;

import com.epsglobal.services.domain.Carrier;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateCarrierResponse {
	@JsonView
	private Long id;
	
	public UpdateCarrierResponse(Carrier carrier) {
		id = carrier.getId();
	}
}
