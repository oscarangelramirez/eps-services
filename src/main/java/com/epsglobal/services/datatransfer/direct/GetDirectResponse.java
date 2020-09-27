package com.epsglobal.services.datatransfer.direct;

import java.math.BigDecimal;

import com.epsglobal.services.datatransfer.file.GetFileResponse;
import com.epsglobal.services.datatransfer.manufacter.GetManufacterResponse;
import com.epsglobal.services.domain.Direct;
import com.fasterxml.jackson.annotation.JsonView;

public class GetDirectResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String description;
	
	@JsonView
	private String manufacterPartNumber;
	
	@JsonView
	private BigDecimal price;
	
	@JsonView
	private BigDecimal cost;
	
	@JsonView
	private BigDecimal profit;
	
	@JsonView
	private GetFileResponse specifications;
	
	@JsonView
	private GetManufacterResponse manufacter;
	
	public GetDirectResponse(Direct direct) {
		id = direct.getId();
		description = direct.getDescription();
		manufacterPartNumber = direct.getManufacterPartNumber();
		price = direct.getPrice();
		cost = direct.getCost();
		profit = direct.getProfit();
		
		if(direct.getSpecifications() != null) {
			specifications = new GetFileResponse(direct.getSpecifications());
		}
		manufacter = new GetManufacterResponse(direct.getManufacter());
	}
}
