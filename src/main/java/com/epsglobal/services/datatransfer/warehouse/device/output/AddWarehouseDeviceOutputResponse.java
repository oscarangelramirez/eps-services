package com.epsglobal.services.datatransfer.warehouse.device.output;

import com.epsglobal.services.domain.WarehouseDeviceOutput;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseDeviceOutputResponse {
	@JsonView
	private Long id;

	public AddWarehouseDeviceOutputResponse(WarehouseDeviceOutput warehouseDeviceOutput) {
		id = warehouseDeviceOutput.getId();
	}
}
