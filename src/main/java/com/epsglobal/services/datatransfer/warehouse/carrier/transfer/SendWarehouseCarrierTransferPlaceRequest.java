package com.epsglobal.services.datatransfer.warehouse.carrier.transfer;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SendWarehouseCarrierTransferPlaceRequest {
	private Long idWarehouse;
	private Long idUser;
	private String comments;
	private Integer complete;
	private Integer partial;
	private BigDecimal priceByReel;
	private BigDecimal costByReel;
	private BigDecimal costByMeter;
	private BigDecimal profit;
}
