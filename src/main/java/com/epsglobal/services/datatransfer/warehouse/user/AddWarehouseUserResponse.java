package com.epsglobal.services.datatransfer.warehouse.user;

import com.epsglobal.services.domain.WarehouseUser;
import com.fasterxml.jackson.annotation.JsonView;

public class AddWarehouseUserResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Long idUser;
	
	public AddWarehouseUserResponse(WarehouseUser warehouseUser) {
		id = warehouseUser.getId();
		idUser = warehouseUser.getUser().getId();
	}
}
