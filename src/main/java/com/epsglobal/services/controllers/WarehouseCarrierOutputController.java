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

import com.epsglobal.services.application.WarehouseCarrierOutputApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.carrier.output.AddWarehouseCarrierOutputRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.output.AddWarehouseCarrierOutputResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.output.GetWarehouseCarrierOutputResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseCarrierOutputController {
	@Autowired
	private WarehouseCarrierOutputApplicationService warehouseCarrierOutputApplicationService;

	@GetMapping("{idWarehouse}/carriers/{idCarrier}/outputs")
	public ResponseEntity<List<GetWarehouseCarrierOutputResponse>> findAll(@PathVariable Long idWarehouse, @PathVariable Long idCarrier) {
		List<GetWarehouseCarrierOutputResponse> response = warehouseCarrierOutputApplicationService.findAll(idWarehouse, idCarrier);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/carriers/{idCarrier}/outputs")
	public ResponseEntity<AddWarehouseCarrierOutputResponse> add(@PathVariable Long idWarehouse, @PathVariable Long idCarrier, @RequestBody AddWarehouseCarrierOutputRequest request) {
		try {
			AddWarehouseCarrierOutputResponse response = warehouseCarrierOutputApplicationService.add(idWarehouse, idCarrier, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
