package com.epsglobal.services.datatransfer.warehouse.carrier.transfer;

import java.math.BigDecimal;
import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.domain.WarehouseCarrierTransferPlace;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseCarrierTransferPlaceResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Date date;
	
	@JsonView
	private String comments;
	
	@JsonView
	private Integer complete;
	
	@JsonView
	private Integer partial;
	
	@JsonView
	private BigDecimal priceByReel;
	
	@JsonView
	private BigDecimal costByReel;
	
	@JsonView
	private BigDecimal costByMeter;
	
	@JsonView
	private BigDecimal profit;
	
	@JsonView
	private GetWarehouseResponse warehouse;
	
	@JsonView
	private GetUserResponse user;
	
	public GetWarehouseCarrierTransferPlaceResponse(WarehouseCarrierTransferPlace warehouseCarrierTransferPlace) {
		id = warehouseCarrierTransferPlace.getId();
		date = warehouseCarrierTransferPlace.getDate();
		comments = warehouseCarrierTransferPlace.getComments();
		complete = warehouseCarrierTransferPlace.getComplete();
		partial = warehouseCarrierTransferPlace.getPartial();
		priceByReel = warehouseCarrierTransferPlace.getPriceByReel();
		costByReel = warehouseCarrierTransferPlace.getCostByReel();
		costByMeter = warehouseCarrierTransferPlace.getCostByMeter();
		profit = warehouseCarrierTransferPlace.getProfit();
		
		warehouse = new GetWarehouseResponse(warehouseCarrierTransferPlace.getWarehouse());
		
		if(warehouseCarrierTransferPlace.getUser() != null) {
			user = new GetUserResponse(warehouseCarrierTransferPlace.getUser());
		}
	}
}
