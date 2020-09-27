package com.epsglobal.services.datatransfer.warehouse.direct.transfer;

import com.epsglobal.services.domain.WarehouseDirectTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class SendWarehouseDirectTransferResponse {
	@JsonView
	private Long id;
	
	public SendWarehouseDirectTransferResponse(WarehouseDirectTransfer warehouseDirectTransfer) {
		id = warehouseDirectTransfer.getId();
	}
}
