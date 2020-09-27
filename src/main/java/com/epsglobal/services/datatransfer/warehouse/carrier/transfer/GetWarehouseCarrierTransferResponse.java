package com.epsglobal.services.datatransfer.warehouse.carrier.transfer;

import com.epsglobal.services.domain.WarehouseCarrierTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseCarrierTransferResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private int status;
	
	@JsonView
	private GetWarehouseCarrierTransferPlaceResponse origin;
	
	@JsonView
	private GetWarehouseCarrierTransferPlaceResponse destination;
	
	public GetWarehouseCarrierTransferResponse(WarehouseCarrierTransfer warehouseCarrierTransfer) {
		id = warehouseCarrierTransfer.getId();
		status = warehouseCarrierTransfer.getStatus().ordinal();
		
		origin = new GetWarehouseCarrierTransferPlaceResponse(warehouseCarrierTransfer.getOrigin());
		destination = new GetWarehouseCarrierTransferPlaceResponse(warehouseCarrierTransfer.getDestination());
	}
}
