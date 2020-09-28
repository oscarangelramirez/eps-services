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

import com.epsglobal.services.application.WarehouseAdapterTransferApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.SendWarehouseAdapterTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.SendWarehouseAdapterTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.GetWarehouseAdapterTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.ReceiveWarehouseAdapterTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.ReceiveWarehouseAdapterTransferResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseAdapterTransferController {
	@Autowired
	private WarehouseAdapterTransferApplicationService warehouseAdapterTransferApplicationService;

	@GetMapping("{idWarehouse}/adapters/{idAdapter}/transfers")
	public ResponseEntity<List<GetWarehouseAdapterTransferResponse>> findAll(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter) {
		List<GetWarehouseAdapterTransferResponse> response = warehouseAdapterTransferApplicationService
				.findAll(idWarehouse, idAdapter);
		return ResponseEntity.ok(response);
	}

	@GetMapping("{idWarehouse}/adapters/{idAdapter}/transfers/{id}")
	public ResponseEntity<GetWarehouseAdapterTransferResponse> findById(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter, @PathVariable Long id) {
		GetWarehouseAdapterTransferResponse response = warehouseAdapterTransferApplicationService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/adapters/{idAdapter}/transfers/send")
	public ResponseEntity<SendWarehouseAdapterTransferResponse> send(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter, @RequestBody SendWarehouseAdapterTransferRequest request) {
		try {
			SendWarehouseAdapterTransferResponse response = warehouseAdapterTransferApplicationService.send(idWarehouse,
					idAdapter, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}

	@PostMapping("{idWarehouse}/adapters/{idAdapter}/transfers/{id}/receive")
	public ResponseEntity<ReceiveWarehouseAdapterTransferResponse> receive(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter, @PathVariable Long id,
			@RequestBody ReceiveWarehouseAdapterTransferRequest request) {
		try {
			ReceiveWarehouseAdapterTransferResponse response = warehouseAdapterTransferApplicationService
					.receive(idWarehouse, idAdapter, id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
