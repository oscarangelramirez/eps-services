package com.epsglobal.services.datatransfer.warehouse.adapter.transfer;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ReceiveWarehouseAdapterTransferPlaceRequest {
	private Long idUser;
	private String comments;
	private Integer quantity;
	private BigDecimal cost;

}
