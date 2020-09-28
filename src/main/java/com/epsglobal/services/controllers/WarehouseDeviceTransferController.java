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

import com.epsglobal.services.application.WarehouseDeviceTransferApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.SendWarehouseDeviceTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.SendWarehouseDeviceTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.GetWarehouseDeviceTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.ReceiveWarehouseDeviceTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.ReceiveWarehouseDeviceTransferResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseDeviceTransferController {
	@Autowired
	private WarehouseDeviceTransferApplicationService warehouseDeviceTransferApplicationService;

	@GetMapping("{idWarehouse}/devices/{idDevice}/transfers")
	public ResponseEntity<List<GetWarehouseDeviceTransferResponse>> findAll(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice) {
		List<GetWarehouseDeviceTransferResponse> response = warehouseDeviceTransferApplicationService
				.findAll(idWarehouse, idDevice);
		return ResponseEntity.ok(response);
	}

	@GetMapping("{idWarehouse}/devices/{idDevice}/transfers/{id}")
	public ResponseEntity<GetWarehouseDeviceTransferResponse> findById(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice, @PathVariable Long id) {
		GetWarehouseDeviceTransferResponse response = warehouseDeviceTransferApplicationService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/devices/{idDevice}/transfers/send")
	public ResponseEntity<SendWarehouseDeviceTransferResponse> send(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice, @RequestBody SendWarehouseDeviceTransferRequest request) {
		try {
			SendWarehouseDeviceTransferResponse response = warehouseDeviceTransferApplicationService.send(idWarehouse,
					idDevice, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}

	@PostMapping("{idWarehouse}/devices/{idDevice}/transfers/{id}/receive")
	public ResponseEntity<ReceiveWarehouseDeviceTransferResponse> receive(@PathVariable Long idWarehouse,
			@PathVariable Long idDevice, @PathVariable Long id,
			@RequestBody ReceiveWarehouseDeviceTransferRequest request) {
		try {
			ReceiveWarehouseDeviceTransferResponse response = warehouseDeviceTransferApplicationService
					.receive(idWarehouse, idDevice, id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
