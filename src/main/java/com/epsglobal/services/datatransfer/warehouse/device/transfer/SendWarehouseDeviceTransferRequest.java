package com.epsglobal.services.datatransfer.warehouse.device.transfer;

import lombok.Data;

@Data
public class SendWarehouseDeviceTransferRequest {
	private SendWarehouseDeviceTransferPlaceRequest origin;
	private SendWarehouseDeviceTransferPlaceRequest destination;
}
