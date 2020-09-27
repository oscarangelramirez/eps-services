package com.epsglobal.services.datatransfer.warehouse;

import com.epsglobal.services.domain.Warehouse;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseResponse {
	@JsonView
	private Long id;
	
	public AddWarehouseResponse(Warehouse warehouse) {
		id = warehouse.getId();
	}
}
