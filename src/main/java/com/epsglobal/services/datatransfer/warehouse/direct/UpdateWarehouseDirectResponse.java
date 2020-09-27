package com.epsglobal.services.datatransfer.warehouse.direct;

import com.epsglobal.services.domain.WarehouseDirect;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateWarehouseDirectResponse {
	@JsonView
	private Long id;
	
	public UpdateWarehouseDirectResponse(WarehouseDirect warehouseDirect) {
		id = warehouseDirect.getId();
	}
}
