package com.epsglobal.services.datatransfer.warehouse.carrier.output;

import com.epsglobal.services.domain.WarehouseCarrierOutput;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseCarrierOutputResponse {
	@JsonView
	private Long id;
	
	public AddWarehouseCarrierOutputResponse(WarehouseCarrierOutput warehouseCarrierOutput) {
		id = warehouseCarrierOutput.getId();
	}
}
