package com.epsglobal.services.datatransfer.warehouse.device.transfer;

import lombok.Data;

@Data
public class ReceiveWarehouseDeviceTransferPlaceRequest {
	private Long idUser;
	private String comments;
	private Integer quantity;

}
