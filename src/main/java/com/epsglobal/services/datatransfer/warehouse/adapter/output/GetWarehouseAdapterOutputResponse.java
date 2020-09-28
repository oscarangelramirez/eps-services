package com.epsglobal.services.datatransfer.warehouse.adapter.output;

import java.math.BigDecimal;
import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.domain.WarehouseAdapterOutput;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseAdapterOutputResponse {
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

	public GetWarehouseAdapterOutputResponse(WarehouseAdapterOutput warehouseAdapterOutput) {
		id = warehouseAdapterOutput.getId();
		date = warehouseAdapterOutput.getDate();
		order = warehouseAdapterOutput.getOrder();
		comments = warehouseAdapterOutput.getComments();
		quantity = warehouseAdapterOutput.getQuantity();
		cost = warehouseAdapterOutput.getCost();

		user = new GetUserResponse(warehouseAdapterOutput.getUser());
	}
}
