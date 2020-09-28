package com.epsglobal.services.datatransfer.warehouse.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class AddWarehouseAdaptersResponse {
	@JsonView
	private List<AddWarehouseAdapterResponse> warehouseAdapters;
	
	public AddWarehouseAdaptersResponse() {
		warehouseAdapters = new ArrayList<AddWarehouseAdapterResponse>();
	}
}
