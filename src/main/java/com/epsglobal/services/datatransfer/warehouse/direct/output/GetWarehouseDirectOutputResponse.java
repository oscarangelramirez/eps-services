package com.epsglobal.services.datatransfer.warehouse.direct.output;

import java.math.BigDecimal;
import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.domain.WarehouseDirectOutput;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDirectOutputResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Date date;
	
	@JsonView
	private String order;
	
	@JsonView
	private String comments;
	
	@JsonView
	private Integer quantity;
	
	@JsonView
	private BigDecimal price;
	
	@JsonView
	private BigDecimal cost;
	
	@JsonView
	private BigDecimal profit;
	
	@JsonView
	private GetUserResponse user;
	
	public GetWarehouseDirectOutputResponse(WarehouseDirectOutput warehouseDirectOutput) {
		id = warehouseDirectOutput.getId();
		date = warehouseDirectOutput.getDate();
		order = warehouseDirectOutput.getOrder();
		comments = warehouseDirectOutput.getComments();
		quantity = warehouseDirectOutput.getQuantity();
		price = warehouseDirectOutput.getPrice();
		cost = warehouseDirectOutput.getCost();
		profit = warehouseDirectOutput.getProfit();
		
		user = new GetUserResponse(warehouseDirectOutput.getUser());
	}
}
