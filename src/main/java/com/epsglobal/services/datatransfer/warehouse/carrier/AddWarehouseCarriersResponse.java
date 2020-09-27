package com.epsglobal.services.datatransfer.warehouse.carrier;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class AddWarehouseCarriersResponse {
	@JsonView
	private List<AddWarehouseCarrierResponse> warehouseCarriers;
	
	public AddWarehouseCarriersResponse() {
		warehouseCarriers = new ArrayList<AddWarehouseCarrierResponse>();
	}
}
