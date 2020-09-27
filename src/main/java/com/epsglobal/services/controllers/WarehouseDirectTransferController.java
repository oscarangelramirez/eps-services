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

import com.epsglobal.services.application.WarehouseDirectTransferApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.SendWarehouseDirectTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.SendWarehouseDirectTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.GetWarehouseDirectTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.ReceiveWarehouseDirectTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.ReceiveWarehouseDirectTransferResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseDirectTransferController {
	@Autowired
	private WarehouseDirectTransferApplicationService warehouseDirectTransferApplicationService;

	@GetMapping("{idWarehouse}/directs/{idDirect}/transfers")
	public ResponseEntity<List<GetWarehouseDirectTransferResponse>> findAll(@PathVariable Long idWarehouse, @PathVariable Long idDirect) {
		List<GetWarehouseDirectTransferResponse> response = warehouseDirectTransferApplicationService.findAll(idWarehouse, idDirect);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{idWarehouse}/directs/{idDirect}/transfers/{id}")
	public ResponseEntity<GetWarehouseDirectTransferResponse> findById(@PathVariable Long idWarehouse, @PathVariable Long idDirect, @PathVariable Long id) {
		GetWarehouseDirectTransferResponse response = warehouseDirectTransferApplicationService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/directs/{idDirect}/transfers/send")
	public ResponseEntity<SendWarehouseDirectTransferResponse> send(@PathVariable Long idWarehouse, @PathVariable Long idDirect, @RequestBody SendWarehouseDirectTransferRequest request) {
		try {
			SendWarehouseDirectTransferResponse response = warehouseDirectTransferApplicationService.send(idWarehouse, idDirect, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PostMapping("{idWarehouse}/directs/{idDirect}/transfers/{id}/receive")
	public ResponseEntity<ReceiveWarehouseDirectTransferResponse> receive(@PathVariable Long idWarehouse, @PathVariable Long idDirect, @PathVariable Long id, @RequestBody ReceiveWarehouseDirectTransferRequest request) {
		try {
			ReceiveWarehouseDirectTransferResponse response = warehouseDirectTransferApplicationService.receive(idWarehouse, idDirect, id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
