package com.epsglobal.services.datatransfer.warehouse.device.transfer;

import com.epsglobal.services.domain.WarehouseDeviceTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class ReceiveWarehouseDeviceTransferResponse {
	@JsonView
	private Long id;

	public ReceiveWarehouseDeviceTransferResponse(WarehouseDeviceTransfer warehouseDeviceTransfer) {
		id = warehouseDeviceTransfer.getId();
	}
}
