package com.epsglobal.services.datatransfer.warehouse.direct;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class AddWarehouseDirectsResponse {
	@JsonView
	private List<AddWarehouseDirectResponse> warehouseDirects;
	
	public AddWarehouseDirectsResponse() {
		warehouseDirects = new ArrayList<AddWarehouseDirectResponse>();
	}
}
