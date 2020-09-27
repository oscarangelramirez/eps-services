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

import com.epsglobal.services.application.WarehouseCarrierTransferApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.SendWarehouseCarrierTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.SendWarehouseCarrierTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.GetWarehouseCarrierTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.ReceiveWarehouseCarrierTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.ReceiveWarehouseCarrierTransferResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseCarrierTransferController {
	@Autowired
	private WarehouseCarrierTransferApplicationService warehouseCarrierTransferApplicationService;

	@GetMapping("{idWarehouse}/carriers/{idCarrier}/transfers")
	public ResponseEntity<List<GetWarehouseCarrierTransferResponse>> findAll(@PathVariable Long idWarehouse, @PathVariable Long idCarrier) {
		List<GetWarehouseCarrierTransferResponse> response = warehouseCarrierTransferApplicationService.findAll(idWarehouse, idCarrier);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{idWarehouse}/carriers/{idCarrier}/transfers/{id}")
	public ResponseEntity<GetWarehouseCarrierTransferResponse> findById(@PathVariable Long idWarehouse, @PathVariable Long idCarrier, @PathVariable Long id) {
		GetWarehouseCarrierTransferResponse response = warehouseCarrierTransferApplicationService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/carriers/{idCarrier}/transfers/send")
	public ResponseEntity<SendWarehouseCarrierTransferResponse> send(@PathVariable Long idWarehouse, @PathVariable Long idCarrier, @RequestBody SendWarehouseCarrierTransferRequest request) {
		try {
			SendWarehouseCarrierTransferResponse response = warehouseCarrierTransferApplicationService.send(idWarehouse, idCarrier, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PostMapping("{idWarehouse}/carriers/{idCarrier}/transfers/{id}/receive")
	public ResponseEntity<ReceiveWarehouseCarrierTransferResponse> receive(@PathVariable Long idWarehouse, @PathVariable Long idCarrier, @PathVariable Long id, @RequestBody ReceiveWarehouseCarrierTransferRequest request) {
		try {
			ReceiveWarehouseCarrierTransferResponse response = warehouseCarrierTransferApplicationService.receive(idWarehouse, idCarrier, id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
