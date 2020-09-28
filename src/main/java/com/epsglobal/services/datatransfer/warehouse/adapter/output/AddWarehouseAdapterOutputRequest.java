package com.epsglobal.services.datatransfer.warehouse.adapter.output;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddWarehouseAdapterOutputRequest {
	private Integer quantity;
	private String comments;
	private String order;
	private BigDecimal cost;
	private Long idUser;
}
