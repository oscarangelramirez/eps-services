package com.epsglobal.services.datatransfer.warehouse.carrier.output;

import java.math.BigDecimal;
import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.domain.WarehouseCarrierOutput;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseCarrierOutputResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Date date;
	
	@JsonView
	private String order;
	
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
	private GetUserResponse user;
	
	public GetWarehouseCarrierOutputResponse(WarehouseCarrierOutput warehouseCarrierOutput) {
		id = warehouseCarrierOutput.getId();
		date = warehouseCarrierOutput.getDate();
		order = warehouseCarrierOutput.getOrder();
		comments = warehouseCarrierOutput.getComments();
		complete = warehouseCarrierOutput.getComplete();
		partial = warehouseCarrierOutput.getPartial();
		priceByReel = warehouseCarrierOutput.getPriceByReel();
		costByReel = warehouseCarrierOutput.getCostByReel();
		costByMeter = warehouseCarrierOutput.getCostByMeter();
		profit = warehouseCarrierOutput.getProfit();
		
		user = new GetUserResponse(warehouseCarrierOutput.getUser());
	}
}
