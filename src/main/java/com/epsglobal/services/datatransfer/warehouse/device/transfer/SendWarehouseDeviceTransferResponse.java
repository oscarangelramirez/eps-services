package com.epsglobal.services.datatransfer.warehouse.device.transfer;

import com.epsglobal.services.domain.WarehouseDeviceTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class SendWarehouseDeviceTransferResponse {
	@JsonView
	private Long id;

	public SendWarehouseDeviceTransferResponse(WarehouseDeviceTransfer warehouseDeviceTransfer) {
		id = warehouseDeviceTransfer.getId();
	}
}
