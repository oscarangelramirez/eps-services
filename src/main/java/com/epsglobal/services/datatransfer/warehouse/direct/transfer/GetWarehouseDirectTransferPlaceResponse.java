package com.epsglobal.services.datatransfer.warehouse.direct.transfer;

import java.math.BigDecimal;
import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.domain.WarehouseDirectTransferPlace;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDirectTransferPlaceResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Date date;
	
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
	private GetWarehouseResponse warehouse;
	
	@JsonView
	private GetUserResponse user;
	
	public GetWarehouseDirectTransferPlaceResponse(WarehouseDirectTransferPlace warehouseDirectTransferPlace) {
		id = warehouseDirectTransferPlace.getId();
		date = warehouseDirectTransferPlace.getDate();
		comments = warehouseDirectTransferPlace.getComments();
		quantity = warehouseDirectTransferPlace.getQuantity();
		price = warehouseDirectTransferPlace.getPrice();
		cost = warehouseDirectTransferPlace.getCost();
		profit = warehouseDirectTransferPlace.getProfit();
		
		warehouse = new GetWarehouseResponse(warehouseDirectTransferPlace.getWarehouse());
		
		if(warehouseDirectTransferPlace.getUser() != null) {
			user = new GetUserResponse(warehouseDirectTransferPlace.getUser());
		}
	}
}
