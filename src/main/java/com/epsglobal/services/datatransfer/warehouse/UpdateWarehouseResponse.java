package com.epsglobal.services.datatransfer.warehouse;

import com.epsglobal.services.domain.Warehouse;
import com.fasterxml.jackson.annotation.JsonView;

public class UpdateWarehouseResponse {
	@JsonView
	private Long id;
	
	public UpdateWarehouseResponse(Warehouse warehouse) {
		id = warehouse.getId();
	}
}
