package com.epsglobal.services.datatransfer.warehouse.device;

import com.epsglobal.services.domain.WarehouseDevice;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateWarehouseDeviceResponse {
	@JsonView
	private Long id;

	public UpdateWarehouseDeviceResponse(WarehouseDevice warehouseDevice) {
		id = warehouseDevice.getId();
	}
}
