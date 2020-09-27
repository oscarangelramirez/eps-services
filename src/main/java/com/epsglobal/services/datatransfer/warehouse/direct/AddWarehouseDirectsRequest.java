package com.epsglobal.services.datatransfer.warehouse.direct;

import java.util.List;

import lombok.Data;

@Data
public class AddWarehouseDirectsRequest {
	private Integer minimium;
	private Integer maximium;
	private List<AddWarehouseDirectRequest> warehouseDirects;
}
