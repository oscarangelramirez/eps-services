package com.epsglobal.services.datatransfer.warehouse.adapter;

import java.util.List;

import lombok.Data;

@Data
public class AddWarehouseAdaptersRequest {

	private List<AddWarehouseAdapterRequest> warehouseAdapters;
}
