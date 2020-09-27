package com.epsglobal.services.datatransfer.warehouse;

import com.epsglobal.services.datatransfer.location.GetLocationResponse;
import com.epsglobal.services.domain.Warehouse;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	@JsonView
	private GetLocationResponse location;
	
	public GetWarehouseResponse(Warehouse warehouse) {
		id = warehouse.getId();
		name = warehouse.getName();
		
		location = new GetLocationResponse(warehouse.getLocation());
	}
}
