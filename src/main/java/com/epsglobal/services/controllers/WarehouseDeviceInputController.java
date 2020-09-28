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

import com.epsglobal.services.application.WarehouseDeviceInputApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.device.input.AddWarehouseDeviceInputRequest;
import com.epsglobal.services.datatransfer.warehouse.device.input.AddWarehouseDeviceInputResponse;
import com.epsglobal.services.datatransfer.warehouse.device.input.GetWarehouseDeviceInputResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseDeviceInputController {
	@Autowired
	private WarehouseDeviceInputApplicationService warehouseDeviceInputApplicationService;

	@GetMapping("{idWarehouse}/devices/{idDevice}/inputs")
	public ResponseEntity<List<GetWarehouseDeviceInputResponse>> findAll(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice) {
		List<GetWarehouseDeviceInputResponse> response = warehouseDeviceInputApplicationService.findAll(idWarehouse,
				idDevice);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/devices/{idDevice}/inputs")
	public ResponseEntity<AddWarehouseDeviceInputResponse> add(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice, @RequestBody AddWarehouseDeviceInputRequest request) {
		try {
			AddWarehouseDeviceInputResponse response = warehouseDeviceInputApplicationService.add(idWarehouse, idDevice,
					request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
