package com.epsglobal.services.datatransfer.device;

import com.epsglobal.services.datatransfer.manufacter.GetManufacterResponse;
import com.epsglobal.services.domain.Device;
import com.fasterxml.jackson.annotation.JsonView;

public class GetDeviceResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String customer;

	@JsonView
	private String manufacterPartNumber;
	
	@JsonView
	private String blankPartNumber;
	
	@JsonView
	private GetManufacterResponse manufacter;
	
	public GetDeviceResponse(Device device) {
		id = device.getId();
		customer = device.getCustomer();
		manufacterPartNumber = device.getManufacterPartNumber();
		blankPartNumber = device.getBlankPartNumber();
		
		manufacter = new GetManufacterResponse(device.getManufacter());
	}
}
