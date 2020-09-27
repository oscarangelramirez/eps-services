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

import com.epsglobal.services.application.WarehouseApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.AddWarehouseRequest;
import com.epsglobal.services.datatransfer.warehouse.AddWarehouseResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.datatransfer.warehouse.UpdateWarehouseRequest;
import com.epsglobal.services.datatransfer.warehouse.UpdateWarehouseResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseController {
	@Autowired
	private WarehouseApplicationService warehouseApplicationService;

	@GetMapping
	public ResponseEntity<List<GetWarehouseResponse>> findAll() {
		List<GetWarehouseResponse> response = warehouseApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetWarehouseResponse> findById(@PathVariable Long id) {
		try {
			GetWarehouseResponse response = warehouseApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddWarehouseResponse> add(@RequestBody AddWarehouseRequest request) {
		try {
			AddWarehouseResponse response = warehouseApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateWarehouseResponse> add(@PathVariable Long id, @RequestBody UpdateWarehouseRequest request) {
		try {
			UpdateWarehouseResponse response = warehouseApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
