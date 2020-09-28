package com.epsglobal.services.datatransfer.warehouse.device.transfer;

import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.domain.WarehouseDeviceTransferPlace;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDeviceTransferPlaceResponse {
	@JsonView
	private Long id;

	@JsonView
	private Date date;

	@JsonView
	private String comments;

	@JsonView
	private Integer quantity;

	@JsonView
	private GetWarehouseResponse warehouse;

	@JsonView
	private GetUserResponse user;

	public GetWarehouseDeviceTransferPlaceResponse(WarehouseDeviceTransferPlace warehouseDeviceTransferPlace) {
		id = warehouseDeviceTransferPlace.getId();
		date = warehouseDeviceTransferPlace.getDate();
		comments = warehouseDeviceTransferPlace.getComments();
		quantity = warehouseDeviceTransferPlace.getQuantity();

		warehouse = new GetWarehouseResponse(warehouseDeviceTransferPlace.getWarehouse());

		if (warehouseDeviceTransferPlace.getUser() != null) {
			user = new GetUserResponse(warehouseDeviceTransferPlace.getUser());
		}
	}
}
