package com.epsglobal.services.datatransfer.warehouse.device;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class AddWarehouseDevicesResponse {
	@JsonView
	private List<AddWarehouseDeviceResponse> warehouseDevices;

	public AddWarehouseDevicesResponse() {
		warehouseDevices = new ArrayList<AddWarehouseDeviceResponse>();
	}
}
