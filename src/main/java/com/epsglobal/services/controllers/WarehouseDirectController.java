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

import com.epsglobal.services.application.WarehouseDirectApplicationService;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.direct.GetDirectResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.AddWarehouseDirectsRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.AddWarehouseDirectsResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.GetWarehouseDirectResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.UpdateWarehouseDirectRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.UpdateWarehouseDirectResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseDirectController {
	@Autowired
	private WarehouseDirectApplicationService warehouseDirectApplicationService;

	@GetMapping("{idWarehouse}/directs")
	public ResponseEntity<List<GetWarehouseDirectResponse>> findAll(@PathVariable Long idWarehouse) {
		List<GetWarehouseDirectResponse> response = warehouseDirectApplicationService.findAll(idWarehouse);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{idWarehouse}/directs/{idDirect}")
	public ResponseEntity<GetWarehouseDirectResponse> findById(@PathVariable Long idWarehouse, @PathVariable Long idDirect) {
		try {
			GetWarehouseDirectResponse response = warehouseDirectApplicationService.findById(idWarehouse, idDirect);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping("{idWarehouse}/directs")
	public ResponseEntity<AddWarehouseDirectsResponse> add(@PathVariable Long idWarehouse, @RequestBody AddWarehouseDirectsRequest request) {
		try {
			AddWarehouseDirectsResponse response = warehouseDirectApplicationService.add(idWarehouse, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{idWarehouse}/directs/{idDirect}")
	public ResponseEntity<UpdateWarehouseDirectResponse> update(@PathVariable Long idWarehouse, @PathVariable Long idDirect, @RequestBody UpdateWarehouseDirectRequest request) {
		try {
			UpdateWarehouseDirectResponse response = warehouseDirectApplicationService.update(idWarehouse, idDirect, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@GetMapping("{idWarehouse}/directs/not")
	public ResponseEntity<List<GetDirectResponse>> findAllNotInWarehouse(@PathVariable Long idWarehouse) {
		List<GetDirectResponse> response = warehouseDirectApplicationService.findAllNotInWarehouse(idWarehouse);
		return ResponseEntity.ok(response);
	}
}
