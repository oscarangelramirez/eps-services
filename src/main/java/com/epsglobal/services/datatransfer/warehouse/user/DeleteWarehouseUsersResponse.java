package com.epsglobal.services.datatransfer.warehouse.user;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class DeleteWarehouseUsersResponse {
	@JsonView
	private List<DeleteWarehouseUserResponse> warehouseUsers;
	
	public DeleteWarehouseUsersResponse() {
		warehouseUsers = new ArrayList<DeleteWarehouseUserResponse>();
	}
}
