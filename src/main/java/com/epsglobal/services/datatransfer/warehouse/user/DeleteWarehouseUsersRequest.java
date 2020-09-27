package com.epsglobal.services.datatransfer.warehouse.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DeleteWarehouseUsersRequest {
	private List<DeleteWarehouseUserRequest> warehouseUsers;
	
	public DeleteWarehouseUsersRequest() {
		warehouseUsers = new ArrayList<DeleteWarehouseUserRequest>();
	}
}
