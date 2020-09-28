package com.epsglobal.services.datatransfer.warehouse.device;

import java.util.List;

import lombok.Data;

@Data
public class AddWarehouseDevicesRequest {

	private List<AddWarehouseDeviceRequest> warehouseDevices;
}
