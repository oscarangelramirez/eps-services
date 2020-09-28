package com.epsglobal.services.datatransfer.warehouse.device.input;

import lombok.Data;

@Data
public class AddWarehouseDeviceInputRequest {
	private Integer quantity;
	private String comments;
	private String order;
	private Long idUser;
}
