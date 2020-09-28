package com.epsglobal.services.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epsglobal.services.application.WarehouseDeviceApplicationService;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.device.GetDeviceResponse;
import com.epsglobal.services.datatransfer.warehouse.device.AddWarehouseDevicesRequest;
import com.epsglobal.services.datatransfer.warehouse.device.AddWarehouseDevicesResponse;
import com.epsglobal.services.datatransfer.warehouse.device.GetWarehouseDeviceResponse;
import com.epsglobal.services.datatransfer.warehouse.device.UpdateWarehouseDeviceRequest;
import com.epsglobal.services.datatransfer.warehouse.device.UpdateWarehouseDeviceResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseDeviceController {
	@Autowired
	private WarehouseDeviceApplicationService warehouseDeviceApplicationService;

	@GetMapping("{idWarehouse}/devices")
	public ResponseEntity<List<GetWarehouseDeviceResponse>> findAll(@PathVariable Long idWarehouse) {
		List<GetWarehouseDeviceResponse> response = warehouseDeviceApplicationService.findAll(idWarehouse);
		return ResponseEntity.ok(response);
	}

	@GetMapping("{idWarehouse}/devices/{idDevice}")
	public ResponseEntity<GetWarehouseDeviceResponse> findById(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice) {
		try {
			GetWarehouseDeviceResponse response = warehouseDeviceApplicationService.findById(idWarehouse, idDevice);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping("{idWarehouse}/devices")
	public ResponseEntity<AddWarehouseDevicesResponse> add(@PathVariable Long idWarehouse,
			@RequestBody AddWarehouseDevicesRequest request) {
		try {
			AddWarehouseDevicesResponse response = warehouseDeviceApplicationService.add(idWarehouse, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PutMapping("{idWarehouse}/devices/{idDevice}")
	public ResponseEntity<UpdateWarehouseDeviceResponse> update(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice, @RequestBody UpdateWarehouseDeviceRequest request) {
		try {
			UpdateWarehouseDeviceResponse response = warehouseDeviceApplicationService.update(idWarehouse, idDevice,
					request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@GetMapping("{idWarehouse}/devices/not")
	public ResponseEntity<List<GetDeviceResponse>> findAllNotInWarehouse(@PathVariable Long idWarehouse) {
		List<GetDeviceResponse> response = warehouseDeviceApplicationService.findAllNotInWarehouse(idWarehouse);
		return ResponseEntity.ok(response);
	}
}
