package com.epsglobal.services.datatransfer.adapter;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UpdateAdapterRequest {
	private Long idManufacter;
	private String manufacterPartNumber;
	private String internalPartNumber;
	private String serial;
	private Boolean isActive;
	private BigDecimal cost;
}
