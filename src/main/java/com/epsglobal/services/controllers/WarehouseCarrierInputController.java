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

import com.epsglobal.services.application.WarehouseCarrierInputApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.carrier.input.AddWarehouseCarrierInputRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.input.AddWarehouseCarrierInputResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.input.GetWarehouseCarrierInputResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseCarrierInputController {
	@Autowired
	private WarehouseCarrierInputApplicationService warehouseCarrierInputApplicationService;

	@GetMapping("{idWarehouse}/carriers/{idCarrier}/inputs")
	public ResponseEntity<List<GetWarehouseCarrierInputResponse>> findAll(@PathVariable Long idWarehouse, @PathVariable Long idCarrier) {
		List<GetWarehouseCarrierInputResponse> response = warehouseCarrierInputApplicationService.findAll(idWarehouse, idCarrier);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/carriers/{idCarrier}/inputs")
	public ResponseEntity<AddWarehouseCarrierInputResponse> add(@PathVariable Long idWarehouse, @PathVariable Long idCarrier, @RequestBody AddWarehouseCarrierInputRequest request) {
		try {
			AddWarehouseCarrierInputResponse response = warehouseCarrierInputApplicationService.add(idWarehouse, idCarrier, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
