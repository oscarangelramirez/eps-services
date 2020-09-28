package com.epsglobal.services.datatransfer.warehouse.adapter.output;

import com.epsglobal.services.domain.WarehouseAdapterOutput;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseAdapterOutputResponse {
	@JsonView
	private Long id;

	public AddWarehouseAdapterOutputResponse(WarehouseAdapterOutput warehouseAdapterOutput) {
		id = warehouseAdapterOutput.getId();
	}
}
