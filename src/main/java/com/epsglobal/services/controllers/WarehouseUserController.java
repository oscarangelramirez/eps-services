package com.epsglobal.services.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epsglobal.services.application.WarehouseUserApplicationService;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.datatransfer.warehouse.user.AddWarehouseUsersRequest;
import com.epsglobal.services.datatransfer.warehouse.user.AddWarehouseUsersResponse;
import com.epsglobal.services.datatransfer.warehouse.user.DeleteWarehouseUsersRequest;
import com.epsglobal.services.datatransfer.warehouse.user.DeleteWarehouseUsersResponse;
import com.epsglobal.services.datatransfer.warehouse.user.GetWarehouseUserResponse;

@RestController
@RequestMapping("/api/v1/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseUserController {
	@Autowired
	private WarehouseUserApplicationService warehouseUserApplicationService;

	@GetMapping("{idWarehouse}/users")
	public ResponseEntity<List<GetWarehouseUserResponse>> findAll(@PathVariable Long idWarehouse) {
		List<GetWarehouseUserResponse> response = warehouseUserApplicationService.findAll(idWarehouse);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idWarehouse}/users")
	public ResponseEntity<AddWarehouseUsersResponse> add(@PathVariable Long idWarehouse, @RequestBody AddWarehouseUsersRequest request) {
		try {
			AddWarehouseUsersResponse response = warehouseUserApplicationService.add(idWarehouse, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@DeleteMapping("{idWarehouse}/users")
	public ResponseEntity<DeleteWarehouseUsersResponse> delete(@PathVariable Long idWarehouse, @RequestBody DeleteWarehouseUsersRequest request) {
		try {
			DeleteWarehouseUsersResponse response = warehouseUserApplicationService.delete(idWarehouse, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@GetMapping("{idWarehouse}/users/not")
	public ResponseEntity<List<GetUserResponse>> findAllNotInRol(@PathVariable Long idWarehouse) {
		try {
			List<GetUserResponse> response = warehouseUserApplicationService.findAllNotInWarehouse(idWarehouse);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
}
