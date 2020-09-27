package com.epsglobal.services.datatransfer.warehouse.carrier.transfer;

import lombok.Data;

@Data
public class SendWarehouseCarrierTransferRequest {
	private SendWarehouseCarrierTransferPlaceRequest origin;
	private SendWarehouseCarrierTransferPlaceRequest destination;
}
