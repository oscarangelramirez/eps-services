package com.epsglobal.services.datatransfer.warehouse.device.input;

import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.domain.WarehouseDeviceInput;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDeviceInputResponse {
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
	private GetUserResponse user;

	public GetWarehouseDeviceInputResponse(WarehouseDeviceInput warehouseDeviceInput) {
		id = warehouseDeviceInput.getId();
		date = warehouseDeviceInput.getDate();
		order = warehouseDeviceInput.getOrder();
		comments = warehouseDeviceInput.getComments();
		quantity = warehouseDeviceInput.getQuantity();
		user = new GetUserResponse(warehouseDeviceInput.getUser());
	}
}
