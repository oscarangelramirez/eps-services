package com.epsglobal.services.datatransfer.warehouse.carrier;

import java.util.List;

import lombok.Data;

@Data
public class AddWarehouseCarriersRequest {
	private Integer minimium;
	private Integer maximium;
	private List<AddWarehouseCarrierRequest> warehouseCarriers;
}
