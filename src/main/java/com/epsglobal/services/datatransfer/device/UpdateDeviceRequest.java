package com.epsglobal.services.datatransfer.device;

import lombok.Data;

@Data
public class UpdateDeviceRequest {
	private Long idManufacter;
	private String customer;
	private String manufacterPartNumber;
	private String blankPartNumber;
}
