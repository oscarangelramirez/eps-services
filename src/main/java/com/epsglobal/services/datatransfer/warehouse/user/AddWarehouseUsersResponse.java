package com.epsglobal.services.datatransfer.warehouse.user;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class AddWarehouseUsersResponse {
	@JsonView
	private List<AddWarehouseUserResponse> warehouseUsers;
	
	public AddWarehouseUsersResponse() {
		warehouseUsers = new ArrayList<AddWarehouseUserResponse>();
	}
}
