package com.epsglobal.services.datatransfer.warehouse.adapter;

import com.epsglobal.services.domain.WarehouseAdapter;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseAdapterResponse {
	@JsonView
	private Long id;

	@JsonView
	private Long idAdapter;

	public AddWarehouseAdapterResponse(WarehouseAdapter warehouseAdapter) {
		id = warehouseAdapter.getId();
		idAdapter = warehouseAdapter.getAdapter().getId();
	}
}
