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

import com.epsglobal.services.application.WarehouseCarrierApplicationService;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.carrier.GetCarrierResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.AddWarehouseCarriersRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.AddWarehouseCarriersResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.GetWarehouseCarrierResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.UpdateWarehouseCarrierRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.UpdateWarehouseCarrierResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseCarrierController {
	@Autowired
	private WarehouseCarrierApplicationService warehouseCarrierApplicationService;

	@GetMapping("{idWarehouse}/carriers")
	public ResponseEntity<List<GetWarehouseCarrierResponse>> findAll(@PathVariable Long idWarehouse) {
		List<GetWarehouseCarrierResponse> response = warehouseCarrierApplicationService.findAll(idWarehouse);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{idWarehouse}/carriers/{idCarrier}")
	public ResponseEntity<GetWarehouseCarrierResponse> findById(@PathVariable Long idWarehouse, @PathVariable Long idCarrier) {
		try {
			GetWarehouseCarrierResponse response = warehouseCarrierApplicationService.findById(idWarehouse, idCarrier);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping("{idWarehouse}/carriers")
	public ResponseEntity<AddWarehouseCarriersResponse> add(@PathVariable Long idWarehouse, @RequestBody AddWarehouseCarriersRequest request) {
		try {
			AddWarehouseCarriersResponse response = warehouseCarrierApplicationService.add(idWarehouse, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{idWarehouse}/carriers/{idCarrier}")
	public ResponseEntity<UpdateWarehouseCarrierResponse> update(@PathVariable Long idWarehouse, @PathVariable Long idCarrier, @RequestBody UpdateWarehouseCarrierRequest request) {
		try {
			UpdateWarehouseCarrierResponse response = warehouseCarrierApplicationService.update(idWarehouse, idCarrier, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@GetMapping("{idWarehouse}/carriers/not")
	public ResponseEntity<List<GetCarrierResponse>> findAllNotInWarehouse(@PathVariable Long idWarehouse) {
		List<GetCarrierResponse> response = warehouseCarrierApplicationService.findAllNotInWarehouse(idWarehouse);
		return ResponseEntity.ok(response);
	}
}
