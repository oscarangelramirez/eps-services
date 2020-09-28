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

import com.epsglobal.services.application.WarehouseAdapterInputApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.adapter.input.AddWarehouseAdapterInputRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.input.AddWarehouseAdapterInputResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.input.GetWarehouseAdapterInputResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseAdapterInputController {
	@Autowired
	private WarehouseAdapterInputApplicationService warehouseAdapterInputApplicationService;

	@GetMapping("{idWarehouse}/adapters/{idAdapter}/inputs")
	public ResponseEntity<List<GetWarehouseAdapterInputResponse>> findAll(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter) {
		List<GetWarehouseAdapterInputResponse> response = warehouseAdapterInputApplicationService.findAll(idWarehouse,
				idAdapter);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/adapters/{idAdapter}/inputs")
	public ResponseEntity<AddWarehouseAdapterInputResponse> add(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter, @RequestBody AddWarehouseAdapterInputRequest request) {
		try {
			AddWarehouseAdapterInputResponse response = warehouseAdapterInputApplicationService.add(idWarehouse,
					idAdapter, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
