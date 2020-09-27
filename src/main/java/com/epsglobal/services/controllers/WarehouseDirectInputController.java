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

import com.epsglobal.services.application.WarehouseDirectInputApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.direct.input.AddWarehouseDirectInputRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.input.AddWarehouseDirectInputResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.input.GetWarehouseDirectInputResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseDirectInputController {
	@Autowired
	private WarehouseDirectInputApplicationService warehouseDirectInputApplicationService;

	@GetMapping("{idWarehouse}/directs/{idDirect}/inputs")
	public ResponseEntity<List<GetWarehouseDirectInputResponse>> findAll(@PathVariable Long idWarehouse, @PathVariable Long idDirect) {
		List<GetWarehouseDirectInputResponse> response = warehouseDirectInputApplicationService.findAll(idWarehouse, idDirect);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/directs/{idDirect}/inputs")
	public ResponseEntity<AddWarehouseDirectInputResponse> add(@PathVariable Long idWarehouse, @PathVariable Long idDirect, @RequestBody AddWarehouseDirectInputRequest request) {
		try {
			AddWarehouseDirectInputResponse response = warehouseDirectInputApplicationService.add(idWarehouse, idDirect, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
