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

import com.epsglobal.services.application.WarehouseAdapterOutputApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.adapter.output.AddWarehouseAdapterOutputRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.output.AddWarehouseAdapterOutputResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.output.GetWarehouseAdapterOutputResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseAdapterOutputController {
	@Autowired
	private WarehouseAdapterOutputApplicationService warehouseAdapterOutputApplicationService;

	@GetMapping("{idWarehouse}/adapters/{idAdapter}/outputs")
	public ResponseEntity<List<GetWarehouseAdapterOutputResponse>> findAll(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter) {
		List<GetWarehouseAdapterOutputResponse> response = warehouseAdapterOutputApplicationService.findAll(idWarehouse,
				idAdapter);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/adapters/{idAdapter}/outputs")
	public ResponseEntity<AddWarehouseAdapterOutputResponse> add(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter, @RequestBody AddWarehouseAdapterOutputRequest request) {
		try {
			AddWarehouseAdapterOutputResponse response = warehouseAdapterOutputApplicationService.add(idWarehouse,
					idAdapter, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
