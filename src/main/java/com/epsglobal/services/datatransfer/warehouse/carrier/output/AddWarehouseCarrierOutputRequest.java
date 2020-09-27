package com.epsglobal.services.datatransfer.warehouse.carrier.output;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddWarehouseCarrierOutputRequest {
	private Integer complete;
	private Integer partial;
	private String comments;
	private String order;
	private BigDecimal priceByReel;
	private BigDecimal costByReel;
	private BigDecimal costByMeter;
	private BigDecimal profit;
	private Long idUser;
}
