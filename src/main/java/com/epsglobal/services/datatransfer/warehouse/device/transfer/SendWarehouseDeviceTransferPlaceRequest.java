package com.epsglobal.services.datatransfer.warehouse.device.transfer;

import lombok.Data;

@Data
public class SendWarehouseDeviceTransferPlaceRequest {
	private Long idWarehouse;
	private Long idUser;
	private String comments;
	private Integer quantity;
}
