package com.epsglobal.services.datatransfer.warehouse.carrier;

import com.epsglobal.services.domain.WarehouseCarrier;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateWarehouseCarrierResponse {
	@JsonView
	private Long id;
	
	public UpdateWarehouseCarrierResponse(WarehouseCarrier warehouseCarrier) {
		id = warehouseCarrier.getId();
	}
}
