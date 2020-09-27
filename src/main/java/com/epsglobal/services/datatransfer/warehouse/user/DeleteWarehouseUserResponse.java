package com.epsglobal.services.datatransfer.warehouse.user;

import com.epsglobal.services.domain.WarehouseUser;
import com.fasterxml.jackson.annotation.JsonView;

public class DeleteWarehouseUserResponse {
	@JsonView
	private Long id;
	
	public DeleteWarehouseUserResponse(WarehouseUser warehouseUser) {
		id = warehouseUser.getId();
	}
}
