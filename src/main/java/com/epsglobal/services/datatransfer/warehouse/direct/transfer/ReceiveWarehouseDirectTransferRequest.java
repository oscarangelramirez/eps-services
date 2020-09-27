package com.epsglobal.services.datatransfer.warehouse.direct.transfer;

import lombok.Data;

@Data
public class ReceiveWarehouseDirectTransferRequest {
	private ReceiveWarehouseDirectTransferPlaceRequest destination;
}
