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

import com.epsglobal.services.application.WarehouseAdapterApplicationService;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.adapter.GetAdapterResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.AddWarehouseAdaptersRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.AddWarehouseAdaptersResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.GetWarehouseAdapterResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.UpdateWarehouseAdapterRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.UpdateWarehouseAdapterResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseAdapterController {
	@Autowired
	private WarehouseAdapterApplicationService warehouseAdapterApplicationService;

	@GetMapping("{idWarehouse}/adapters")
	public ResponseEntity<List<GetWarehouseAdapterResponse>> findAll(@PathVariable Long idWarehouse) {
		List<GetWarehouseAdapterResponse> response = warehouseAdapterApplicationService.findAll(idWarehouse);
		return ResponseEntity.ok(response);
	}

	@GetMapping("{idWarehouse}/adapters/{idAdapter}")
	public ResponseEntity<GetWarehouseAdapterResponse> findById(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter) {
		try {
			GetWarehouseAdapterResponse response = warehouseAdapterApplicationService.findById(idWarehouse, idAdapter);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping("{idWarehouse}/adapters")
	public ResponseEntity<AddWarehouseAdaptersResponse> add(@PathVariable Long idWarehouse,
			@RequestBody AddWarehouseAdaptersRequest request) {
		try {
			AddWarehouseAdaptersResponse response = warehouseAdapterApplicationService.add(idWarehouse, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PutMapping("{idWarehouse}/adapters/{idAdapter}")
	public ResponseEntity<UpdateWarehouseAdapterResponse> update(@PathVariable Long idWarehouse,
			@PathVariable Long idAdapter, @RequestBody UpdateWarehouseAdapterRequest request) {
		try {
			UpdateWarehouseAdapterResponse response = warehouseAdapterApplicationService.update(idWarehouse, idAdapter,
					request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@GetMapping("{idWarehouse}/adapters/not")
	public ResponseEntity<List<GetAdapterResponse>> findAllNotInWarehouse(@PathVariable Long idWarehouse) {
		List<GetAdapterResponse> response = warehouseAdapterApplicationService.findAllNotInWarehouse(idWarehouse);
		return ResponseEntity.ok(response);
	}
}
