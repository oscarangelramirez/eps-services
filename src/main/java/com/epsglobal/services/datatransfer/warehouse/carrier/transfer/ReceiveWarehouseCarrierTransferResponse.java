package com.epsglobal.services.datatransfer.warehouse.carrier.transfer;

import com.epsglobal.services.domain.WarehouseCarrierTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class ReceiveWarehouseCarrierTransferResponse {
	@JsonView
	private Long id;
	
	public ReceiveWarehouseCarrierTransferResponse(WarehouseCarrierTransfer warehouseCarrierTransfer) {
		id = warehouseCarrierTransfer.getId();
	}
}
