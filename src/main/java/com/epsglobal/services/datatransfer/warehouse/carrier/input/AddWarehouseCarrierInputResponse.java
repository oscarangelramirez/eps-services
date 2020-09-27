package com.epsglobal.services.datatransfer.warehouse.carrier.input;

import com.epsglobal.services.domain.WarehouseCarrierInput;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseCarrierInputResponse {
	@JsonView
	private Long id;
	
	public AddWarehouseCarrierInputResponse(WarehouseCarrierInput warehouseCarrierInput) {
		id = warehouseCarrierInput.getId();
	}
}
