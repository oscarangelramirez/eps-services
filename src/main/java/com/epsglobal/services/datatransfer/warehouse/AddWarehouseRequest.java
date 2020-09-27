package com.epsglobal.services.datatransfer.warehouse;

import lombok.Data;

@Data
public class AddWarehouseRequest {
	private Long idLocation;
	private String name;
}
