package com.epsglobal.services.datatransfer.warehouse.direct.input;

import com.epsglobal.services.domain.WarehouseDirectInput;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseDirectInputResponse {
	@JsonView
	private Long id;
	
	public AddWarehouseDirectInputResponse(WarehouseDirectInput warehouseDirectInput) {
		id = warehouseDirectInput.getId();
	}
}
