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

import com.epsglobal.services.application.WarehouseDirectOutputApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.direct.output.AddWarehouseDirectOutputRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.output.AddWarehouseDirectOutputResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.output.GetWarehouseDirectOutputResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseDirectOutputController {
	@Autowired
	private WarehouseDirectOutputApplicationService warehouseDirectOutputApplicationService;

	@GetMapping("{idWarehouse}/directs/{idDirect}/outputs")
	public ResponseEntity<List<GetWarehouseDirectOutputResponse>> findAll(@PathVariable Long idWarehouse, @PathVariable Long idDirect) {
		List<GetWarehouseDirectOutputResponse> response = warehouseDirectOutputApplicationService.findAll(idWarehouse, idDirect);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/directs/{idDirect}/outputs")
	public ResponseEntity<AddWarehouseDirectOutputResponse> add(@PathVariable Long idWarehouse, @PathVariable Long idDirect, @RequestBody AddWarehouseDirectOutputRequest request) {
		try {
			AddWarehouseDirectOutputResponse response = warehouseDirectOutputApplicationService.add(idWarehouse, idDirect, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
