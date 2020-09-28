package com.epsglobal.services.datatransfer.warehouse.device.input;

import com.epsglobal.services.domain.WarehouseDeviceInput;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseDeviceInputResponse {
	@JsonView
	private Long id;

	public AddWarehouseDeviceInputResponse(WarehouseDeviceInput warehouseDeviceInput) {
		id = warehouseDeviceInput.getId();
	}
}
