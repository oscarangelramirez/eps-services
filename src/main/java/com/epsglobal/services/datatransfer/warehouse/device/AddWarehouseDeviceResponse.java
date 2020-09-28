package com.epsglobal.services.datatransfer.warehouse.device;

import com.epsglobal.services.domain.WarehouseDevice;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseDeviceResponse {
	@JsonView
	private Long id;

	@JsonView
	private Long idDevice;

	public AddWarehouseDeviceResponse(WarehouseDevice warehouseDevice) {
		id = warehouseDevice.getId();
		idDevice = warehouseDevice.getDevice().getId();
	}
}
