package com.epsglobal.services.datatransfer.warehouse.carrier;

import com.epsglobal.services.datatransfer.carrier.GetCarrierResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.domain.WarehouseCarrier;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseCarrierResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Integer minimium;
	
	@JsonView
	private Integer maximium;
	
	@JsonView
	private Long onHandComplete;
	
	@JsonView
	private Long onHandPartial;
	
	@JsonView
	private GetWarehouseResponse warehouse;
	
	@JsonView
	private GetCarrierResponse carrier;
	
	public GetWarehouseCarrierResponse(WarehouseCarrier warehouseCarrier) {
		id = warehouseCarrier.getId();
		minimium = warehouseCarrier.getMinimium();
		maximium = warehouseCarrier.getMaximium();
		onHandComplete = warehouseCarrier.getOnHandComplete();
		onHandPartial = warehouseCarrier.getOnHandPartial();
		
		warehouse = new GetWarehouseResponse(warehouseCarrier.getWarehouse());
		carrier = new GetCarrierResponse(warehouseCarrier.getCarrier());
	}
}
