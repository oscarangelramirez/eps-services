package com.epsglobal.services.datatransfer.direct;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddDirectRequest {
	private Long idManufacter;
	private Long idFile;
	private String description;
	private String manufacterPartNumber;
	private BigDecimal price;
	private BigDecimal cost;
	private BigDecimal profit;
}
