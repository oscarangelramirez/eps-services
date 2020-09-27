package com.epsglobal.services.datatransfer.warehouse.direct.transfer;

import com.epsglobal.services.domain.WarehouseDirectTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class ReceiveWarehouseDirectTransferResponse {
	@JsonView
	private Long id;
	
	public ReceiveWarehouseDirectTransferResponse(WarehouseDirectTransfer warehouseDirectTransfer) {
		id = warehouseDirectTransfer.getId();
	}
}
