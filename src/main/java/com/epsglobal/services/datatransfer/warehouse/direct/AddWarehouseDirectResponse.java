package com.epsglobal.services.datatransfer.warehouse.direct;

import com.epsglobal.services.domain.WarehouseDirect;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseDirectResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Long idDirect;
	
	public AddWarehouseDirectResponse(WarehouseDirect warehouseDirect) {
		id = warehouseDirect.getId();
		idDirect = warehouseDirect.getDirect().getId();
	}
}
