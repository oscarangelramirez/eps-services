package com.epsglobal.services.datatransfer.warehouse.direct.input;

import java.math.BigDecimal;
import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.domain.WarehouseDirectInput;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDirectInputResponse {
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
	
	public GetWarehouseDirectInputResponse(WarehouseDirectInput warehouseDirectInput) {
		id = warehouseDirectInput.getId();
		date = warehouseDirectInput.getDate();
		order = warehouseDirectInput.getOrder();
		comments = warehouseDirectInput.getComments();
		quantity = warehouseDirectInput.getQuantity();
		price = warehouseDirectInput.getPrice();
		cost = warehouseDirectInput.getCost();
		profit = warehouseDirectInput.getProfit();
		
		user = new GetUserResponse(warehouseDirectInput.getUser());
	}
}
