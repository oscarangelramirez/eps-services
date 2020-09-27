package com.epsglobal.services.datatransfer.device;

import com.epsglobal.services.domain.Device;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateDeviceResponse {
	@JsonView
	private Long id;
	
	public UpdateDeviceResponse(Device device) {
		id = device.getId();
	}
}
