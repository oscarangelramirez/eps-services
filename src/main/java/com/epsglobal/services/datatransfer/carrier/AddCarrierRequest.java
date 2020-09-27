package com.epsglobal.services.datatransfer.carrier;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddCarrierRequest {
	private Long idManufacter;
	private Long idFile;
	private String description;
	private String manufacterPartNumber;
	private String encapsulated;
	private Short w;
	private BigDecimal a0;
	private BigDecimal b0;
	private BigDecimal k0;
	private Integer p;
	private BigDecimal t;
	private Integer metersByReel;
	private Integer pocketByReel;
	private BigDecimal priceByReel;
	private BigDecimal costByReel;
	private BigDecimal costByMeter;
	private BigDecimal profit;
}
