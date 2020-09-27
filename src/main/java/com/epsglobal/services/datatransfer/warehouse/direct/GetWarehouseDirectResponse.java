package com.epsglobal.services.datatransfer.warehouse.direct;

import com.epsglobal.services.datatransfer.direct.GetDirectResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.domain.WarehouseDirect;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDirectResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Integer minimium;
	
	@JsonView
	private Integer maximium;
	
	@JsonView
	private Long onHand;
	
	@JsonView
	private GetWarehouseResponse warehouse;
	
	@JsonView
	private GetDirectResponse direct;
	
	public GetWarehouseDirectResponse(WarehouseDirect warehouseDirect) {
		id = warehouseDirect.getId();
		minimium = warehouseDirect.getMinimium();
		maximium = warehouseDirect.getMaximium();
		onHand = warehouseDirect.getOnHand();
		
		warehouse = new GetWarehouseResponse(warehouseDirect.getWarehouse());
		direct = new GetDirectResponse(warehouseDirect.getDirect());
	}
}
