package com.epsglobal.services.datatransfer.warehouse.direct.transfer;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SendWarehouseDirectTransferPlaceRequest {
	private Long idWarehouse;
	private Long idUser;
	private String comments;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal cost;
	private BigDecimal profit;
}
