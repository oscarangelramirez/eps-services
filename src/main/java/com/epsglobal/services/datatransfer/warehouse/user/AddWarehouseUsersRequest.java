package com.epsglobal.services.datatransfer.warehouse.user;

import java.util.List;

import lombok.Data;

@Data
public class AddWarehouseUsersRequest {
	private List<AddWarehouseUserRequest> warehouseUsers;
}
