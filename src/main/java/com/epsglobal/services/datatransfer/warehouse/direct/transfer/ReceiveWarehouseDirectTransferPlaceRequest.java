package com.epsglobal.services.datatransfer.warehouse.direct.transfer;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ReceiveWarehouseDirectTransferPlaceRequest {
	private Long idUser;
	private String comments;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal cost;
	private BigDecimal profit;
}
