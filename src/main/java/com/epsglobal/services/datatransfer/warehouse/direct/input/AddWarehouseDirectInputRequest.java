package com.epsglobal.services.datatransfer.warehouse.direct.input;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddWarehouseDirectInputRequest {
	private Integer quantity;
	private String comments;
	private String order;
	private BigDecimal price;
	private BigDecimal cost;
	private BigDecimal profit;
	private Long idUser;
}
