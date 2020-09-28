package com.epsglobal.services.datatransfer.warehouse.adapter.transfer;

import com.epsglobal.services.domain.WarehouseAdapterTransfer;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseAdapterTransferResponse {
	@JsonView
	private Long id;

	@JsonView
	private int status;

	@JsonView
	private GetWarehouseAdapterTransferPlaceResponse origin;

	@JsonView
	private GetWarehouseAdapterTransferPlaceResponse destination;

	public GetWarehouseAdapterTransferResponse(WarehouseAdapterTransfer warehouseAdapterTransfer) {
		id = warehouseAdapterTransfer.getId();
		status = warehouseAdapterTransfer.getStatus().ordinal();

		origin = new GetWarehouseAdapterTransferPlaceResponse(warehouseAdapterTransfer.getOrigin());
		destination = new GetWarehouseAdapterTransferPlaceResponse(warehouseAdapterTransfer.getDestination());
	}
}
