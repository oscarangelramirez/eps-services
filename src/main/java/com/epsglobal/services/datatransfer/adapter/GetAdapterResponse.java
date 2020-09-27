package com.epsglobal.services.datatransfer.adapter;

import java.math.BigDecimal;

import com.epsglobal.services.datatransfer.manufacter.GetManufacterResponse;
import com.epsglobal.services.domain.Adapter;
import com.fasterxml.jackson.annotation.JsonView;

public class GetAdapterResponse {
	@JsonView
	private Long id;

	@JsonView
	private String manufacterPartNumber;
	
	@JsonView
	private String internalPartNumber;
	
	@JsonView
	private String serial;
	
	@JsonView
	private Boolean isActive;
	
	@JsonView
	private BigDecimal cost;
	
	@JsonView
	private GetManufacterResponse manufacter;
	
	public GetAdapterResponse(Adapter adapter) {
		id = adapter.getId();
		manufacterPartNumber = adapter.getManufacterPartNumber();
		internalPartNumber = adapter.getInternalPartNumber();
		serial = adapter.getSerial();
		isActive = adapter.getIsActive();
		cost = adapter.getCost();
		
		manufacter = new GetManufacterResponse(adapter.getManufacter());
	}
}
