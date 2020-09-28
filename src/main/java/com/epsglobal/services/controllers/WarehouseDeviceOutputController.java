package com.epsglobal.services.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epsglobal.services.application.WarehouseDeviceOutputApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.device.output.AddWarehouseDeviceOutputRequest;
import com.epsglobal.services.datatransfer.warehouse.device.output.AddWarehouseDeviceOutputResponse;
import com.epsglobal.services.datatransfer.warehouse.device.output.GetWarehouseDeviceOutputResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseDeviceOutputController {
	@Autowired
	private WarehouseDeviceOutputApplicationService warehouseDeviceOutputApplicationService;

	@GetMapping("{idWarehouse}/devices/{idDevice}/outputs")
	public ResponseEntity<List<GetWarehouseDeviceOutputResponse>> findAll(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice) {
		List<GetWarehouseDeviceOutputResponse> response = warehouseDeviceOutputApplicationService.findAll(idWarehouse,
				idDevice);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/devices/{idDevice}/outputs")
	public ResponseEntity<AddWarehouseDeviceOutputResponse> add(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice, @RequestBody AddWarehouseDeviceOutputRequest request) {
		try {
			AddWarehouseDeviceOutputResponse response = warehouseDeviceOutputApplicationService.add(idWarehouse,
					idDevice, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
