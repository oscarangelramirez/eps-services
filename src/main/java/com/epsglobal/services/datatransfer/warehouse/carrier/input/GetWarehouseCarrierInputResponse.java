package com.epsglobal.services.datatransfer.warehouse.carrier.input;

import java.math.BigDecimal;
import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.domain.WarehouseCarrierInput;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseCarrierInputResponse {
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
	
	public GetWarehouseCarrierInputResponse(WarehouseCarrierInput warehouseCarrierInput) {
		id = warehouseCarrierInput.getId();
		date = warehouseCarrierInput.getDate();
		order = warehouseCarrierInput.getOrder();
		comments = warehouseCarrierInput.getComments();
		complete = warehouseCarrierInput.getComplete();
		partial = warehouseCarrierInput.getPartial();
		priceByReel = warehouseCarrierInput.getPriceByReel();
		costByReel = warehouseCarrierInput.getCostByReel();
		costByMeter = warehouseCarrierInput.getCostByMeter();
		profit = warehouseCarrierInput.getProfit();
		
		user = new GetUserResponse(warehouseCarrierInput.getUser());
	}
}
