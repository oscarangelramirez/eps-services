package com.epsglobal.services.datatransfer.warehouse.adapter.input;

import java.math.BigDecimal;
import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.domain.WarehouseAdapterInput;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseAdapterInputResponse {
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
	private BigDecimal cost;

	@JsonView
	private GetUserResponse user;

	public GetWarehouseAdapterInputResponse(WarehouseAdapterInput warehouseAdapterInput) {
		id = warehouseAdapterInput.getId();
		date = warehouseAdapterInput.getDate();
		order = warehouseAdapterInput.getOrder();
		comments = warehouseAdapterInput.getComments();
		quantity = warehouseAdapterInput.getQuantity();
		cost = warehouseAdapterInput.getCost();
		user = new GetUserResponse(warehouseAdapterInput.getUser());
	}
}
