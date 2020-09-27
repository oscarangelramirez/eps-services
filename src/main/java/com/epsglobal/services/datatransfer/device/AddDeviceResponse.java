package com.epsglobal.services.datatransfer.device;

import com.epsglobal.services.domain.Device;
import com.fasterxml.jackson.annotation.JsonView;

public class AddDeviceResponse {
	@JsonView
	private Long id;
	
	public AddDeviceResponse(Device device) {
		id = device.getId();
	}
}
