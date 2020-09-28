package com.epsglobal.services.datatransfer.warehouse.adapter.transfer;

import java.math.BigDecimal;
import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.domain.WarehouseAdapterTransferPlace;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseAdapterTransferPlaceResponse {
	@JsonView
	private Long id;

	@JsonView
	private Date date;

	@JsonView
	private String comments;

	@JsonView
	private Integer quantity;

	@JsonView
	private BigDecimal cost;

	@JsonView
	private GetWarehouseResponse warehouse;

	@JsonView
	private GetUserResponse user;

	public GetWarehouseAdapterTransferPlaceResponse(WarehouseAdapterTransferPlace warehouseAdapterTransferPlace) {
		id = warehouseAdapterTransferPlace.getId();
		date = warehouseAdapterTransferPlace.getDate();
		comments = warehouseAdapterTransferPlace.getComments();
		quantity = warehouseAdapterTransferPlace.getQuantity();
		cost = warehouseAdapterTransferPlace.getCost();

		warehouse = new GetWarehouseResponse(warehouseAdapterTransferPlace.getWarehouse());

		if (warehouseAdapterTransferPlace.getUser() != null) {
			user = new GetUserResponse(warehouseAdapterTransferPlace.getUser());
		}
	}
}
