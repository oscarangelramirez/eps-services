package com.epsglobal.services.datatransfer.warehouse;

import lombok.Data;

@Data
public class UpdateWarehouseRequest {
	private Long idLocation;
	private String name;
}
