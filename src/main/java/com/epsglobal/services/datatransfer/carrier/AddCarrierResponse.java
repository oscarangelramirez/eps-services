package com.epsglobal.services.datatransfer.carrier;

import com.epsglobal.services.domain.Carrier;
import com.fasterxml.jackson.annotation.JsonView;

public class AddCarrierResponse {
	@JsonView
	private Long id;
	
	public AddCarrierResponse(Carrier carrier) {
		id = carrier.getId();
	}
}
