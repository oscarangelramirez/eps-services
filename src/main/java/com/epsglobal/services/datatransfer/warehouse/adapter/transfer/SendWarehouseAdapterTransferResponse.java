package com.epsglobal.services.datatransfer.warehouse.adapter.transfer;

import com.epsglobal.services.domain.WarehouseAdapterTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class SendWarehouseAdapterTransferResponse {
	@JsonView
	private Long id;

	public SendWarehouseAdapterTransferResponse(WarehouseAdapterTransfer warehouseAdapterTransfer) {
		id = warehouseAdapterTransfer.getId();
	}
}
