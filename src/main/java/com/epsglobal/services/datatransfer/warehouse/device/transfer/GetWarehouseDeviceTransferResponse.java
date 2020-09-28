package com.epsglobal.services.datatransfer.warehouse.device.transfer;

import com.epsglobal.services.domain.WarehouseDeviceTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDeviceTransferResponse {
	@JsonView
	private Long id;

	@JsonView
	private int status;

	@JsonView
	private GetWarehouseDeviceTransferPlaceResponse origin;

	@JsonView
	private GetWarehouseDeviceTransferPlaceResponse destination;

	public GetWarehouseDeviceTransferResponse(WarehouseDeviceTransfer warehouseDeviceTransfer) {
		id = warehouseDeviceTransfer.getId();
		status = warehouseDeviceTransfer.getStatus().ordinal();

		origin = new GetWarehouseDeviceTransferPlaceResponse(warehouseDeviceTransfer.getOrigin());
		destination = new GetWarehouseDeviceTransferPlaceResponse(warehouseDeviceTransfer.getDestination());
	}
}
