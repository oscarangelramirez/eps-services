package com.epsglobal.services.datatransfer.warehouse.device;

import com.epsglobal.services.datatransfer.device.GetDeviceResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.domain.WarehouseDevice;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDeviceResponse {
	@JsonView
	private Long id;

	@JsonView
	private Long onHand;

	@JsonView
	private GetWarehouseResponse warehouse;

	@JsonView
	private GetDeviceResponse device;

	public GetWarehouseDeviceResponse(WarehouseDevice warehouseDevice) {
		id = warehouseDevice.getId();
		onHand = warehouseDevice.getOnHand();

		warehouse = new GetWarehouseResponse(warehouseDevice.getWarehouse());
		device = new GetDeviceResponse(warehouseDevice.getDevice());
	}
}
