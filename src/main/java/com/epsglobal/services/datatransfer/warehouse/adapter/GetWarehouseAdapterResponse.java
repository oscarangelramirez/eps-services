package com.epsglobal.services.datatransfer.warehouse.adapter;

import com.epsglobal.services.datatransfer.adapter.GetAdapterResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.domain.WarehouseAdapter;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseAdapterResponse {
	@JsonView
	private Long id;

	@JsonView
	private Long onHand;

	@JsonView
	private GetWarehouseResponse warehouse;

	@JsonView
	private GetAdapterResponse adapter;

	public GetWarehouseAdapterResponse(WarehouseAdapter warehouseAdapter) {
		id = warehouseAdapter.getId();
		onHand = warehouseAdapter.getOnHand();

		warehouse = new GetWarehouseResponse(warehouseAdapter.getWarehouse());
		adapter = new GetAdapterResponse(warehouseAdapter.getAdapter());
	}
}
