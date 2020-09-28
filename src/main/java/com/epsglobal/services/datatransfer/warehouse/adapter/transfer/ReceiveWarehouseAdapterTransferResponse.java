package com.epsglobal.services.datatransfer.warehouse.adapter.transfer;

import com.epsglobal.services.domain.WarehouseAdapterTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class ReceiveWarehouseAdapterTransferResponse {
	@JsonView
	private Long id;

	public ReceiveWarehouseAdapterTransferResponse(WarehouseAdapterTransfer warehouseAdapterTransfer) {
		id = warehouseAdapterTransfer.getId();
	}
}
