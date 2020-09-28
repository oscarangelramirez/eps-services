package com.epsglobal.services.datatransfer.warehouse.adapter;

import com.epsglobal.services.domain.WarehouseAdapter;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateWarehouseAdapterResponse {
	@JsonView
	private Long id;

	public UpdateWarehouseAdapterResponse(WarehouseAdapter warehouseAdapter) {
		id = warehouseAdapter.getId();
	}
}
