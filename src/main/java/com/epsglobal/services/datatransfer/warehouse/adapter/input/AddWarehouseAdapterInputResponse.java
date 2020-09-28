package com.epsglobal.services.datatransfer.warehouse.adapter.input;

import com.epsglobal.services.domain.WarehouseAdapterInput;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseAdapterInputResponse {
	@JsonView
	private Long id;

	public AddWarehouseAdapterInputResponse(WarehouseAdapterInput warehouseAdapterInput) {
		id = warehouseAdapterInput.getId();
	}
}
