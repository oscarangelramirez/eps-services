package com.epsglobal.services.datatransfer.warehouse.adapter.input;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddWarehouseAdapterInputRequest {
	private Integer quantity;
	private String comments;
	private String order;
	private BigDecimal cost;
	private Long idUser;
}
