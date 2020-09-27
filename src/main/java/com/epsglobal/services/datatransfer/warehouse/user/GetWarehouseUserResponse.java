package com.epsglobal.services.datatransfer.warehouse.user;

import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.domain.WarehouseUser;
import com.fasterxml.jackson.annotation.JsonView;

public class GetWarehouseUserResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private GetUserResponse user;
	
	public GetWarehouseUserResponse(WarehouseUser warehouseUser) {
		id = warehouseUser.getId();
		
		user = new GetUserResponse(warehouseUser.getUser());
	}
}
