package com.epsglobal.services.datatransfer.warehouse.device.output;

import java.util.Date;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.domain.WarehouseDeviceOutput;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseDeviceOutputResponse {
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

	public GetWarehouseDeviceOutputResponse(WarehouseDeviceOutput warehouseDeviceOutput) {
		id = warehouseDeviceOutput.getId();
		date = warehouseDeviceOutput.getDate();
		order = warehouseDeviceOutput.getOrder();
		comments = warehouseDeviceOutput.getComments();
		quantity = warehouseDeviceOutput.getQuantity();

		user = new GetUserResponse(warehouseDeviceOutput.getUser());
	}
}
