package com.epsglobal.services.datatransfer.warehouse.direct.transfer;

import lombok.Data;

@Data
public class SendWarehouseDirectTransferRequest {
	private SendWarehouseDirectTransferPlaceRequest origin;
	private SendWarehouseDirectTransferPlaceRequest destination;
}
