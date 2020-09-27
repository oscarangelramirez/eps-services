package com.epsglobal.services.datatransfer.carrier;

import java.math.BigDecimal;

import com.epsglobal.services.datatransfer.file.GetFileResponse;
import com.epsglobal.services.datatransfer.manufacter.GetManufacterResponse;
import com.epsglobal.services.domain.Carrier;
import com.fasterxml.jackson.annotation.JsonView;

public class GetCarrierResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String description;
	
	@JsonView
	private String manufacterPartNumber;
	
	@JsonView
	private String encapsulated;
	
	@JsonView
	private Short w;
	
	@JsonView
	private BigDecimal a0;
	
	@JsonView
	private BigDecimal b0;
	
	@JsonView
	private BigDecimal k0;
	
	@JsonView
	private Integer p;
	
	@JsonView
	private BigDecimal t;
	
	@JsonView
	private Integer metersByReel;
	
	@JsonView
	private Integer pocketByReel;
	
	@JsonView
	private BigDecimal priceByReel;
	
	@JsonView
	private BigDecimal costByReel;
	
	@JsonView
	private BigDecimal costByMeter;
	
	@JsonView
	private BigDecimal profit;
	
	@JsonView
	private GetFileResponse drawing;
	
	@JsonView
	private GetManufacterResponse manufacter;
	
	public GetCarrierResponse(Carrier carrier) {
		id = carrier.getId();
		description = carrier.getDescription();
		manufacterPartNumber = carrier.getManufacterPartNumber();
		encapsulated = carrier.getEncapsulated();
		w = carrier.getW();
		a0 = carrier.getA0();
		b0 = carrier.getB0();
		k0 = carrier.getK0();
		p = carrier.getP();
		t = carrier.getT();
		metersByReel = carrier.getMetersByReel();
		pocketByReel = carrier.getPocketByReel();
		priceByReel = carrier.getPriceByReel();
		costByReel = carrier.getCostByReel();
		costByMeter = carrier.getCostByMeter();
		profit = carrier.getProfit();
		
		if(carrier.getDrawing() != null) {
			drawing = new GetFileResponse(carrier.getDrawing());
		}
		
		manufacter = new GetManufacterResponse(carrier.getManufacter());
	}
}
