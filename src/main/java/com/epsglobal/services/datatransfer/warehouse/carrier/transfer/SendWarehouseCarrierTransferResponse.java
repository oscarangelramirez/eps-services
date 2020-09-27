package com.epsglobal.services.datatransfer.warehouse.carrier.transfer;

import com.epsglobal.services.domain.WarehouseCarrierTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class SendWarehouseCarrierTransferResponse {
	@JsonView
	private Long id;
	
	public SendWarehouseCarrierTransferResponse(WarehouseCarrierTransfer warehouseCarrierTransfer) {
		id = warehouseCarrierTransfer.getId();
	}
}
