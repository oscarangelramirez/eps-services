package com.epsglobal.services.datatransfer.warehouse.device.output;

import lombok.Data;

@Data
public class AddWarehouseDeviceOutputRequest {
	private Integer quantity;
	private String comments;
	private String order;
	private Long idUser;
}
