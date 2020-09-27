package com.epsglobal.services.datatransfer.warehouse.direct.output;

import com.epsglobal.services.domain.WarehouseDirectOutput;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseDirectOutputResponse {
	@JsonView
	private Long id;
	
	public AddWarehouseDirectOutputResponse(WarehouseDirectOutput warehouseDirectOutput) {
		id = warehouseDirectOutput.getId();
	}
}
