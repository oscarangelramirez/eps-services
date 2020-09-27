package com.epsglobal.services.datatransfer.warehouse.direct.transfer;

import com.epsglobal.services.domain.WarehouseDirectTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDirectTransferResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private int status;
	
	@JsonView
	private GetWarehouseDirectTransferPlaceResponse origin;
	
	@JsonView
	private GetWarehouseDirectTransferPlaceResponse destination;
	
	public GetWarehouseDirectTransferResponse(WarehouseDirectTransfer warehouseDirectTransfer) {
		id = warehouseDirectTransfer.getId();
		status = warehouseDirectTransfer.getStatus().ordinal();
		
		origin = new GetWarehouseDirectTransferPlaceResponse(warehouseDirectTransfer.getOrigin());
		destination = new GetWarehouseDirectTransferPlaceResponse(warehouseDirectTransfer.getDestination());
	}
}
