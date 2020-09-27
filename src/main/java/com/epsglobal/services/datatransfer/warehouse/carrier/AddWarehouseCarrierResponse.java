package com.epsglobal.services.datatransfer.warehouse.carrier;

import com.epsglobal.services.domain.WarehouseCarrier;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseCarrierResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Long idCarrier;
	
	public AddWarehouseCarrierResponse(WarehouseCarrier warehouseCarrier) {
		id = warehouseCarrier.getId();
		idCarrier = warehouseCarrier.getCarrier().getId();
	}
}
