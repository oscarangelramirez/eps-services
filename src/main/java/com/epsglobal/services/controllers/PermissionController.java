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

import com.epsglobal.services.application.PermissionApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.permission.AddPermissionRequest;
import com.epsglobal.services.datatransfer.permission.AddPermissionResponse;
import com.epsglobal.services.datatransfer.permission.GetPermissionResponse;
import com.epsglobal.services.datatransfer.permission.UpdatePermissionRequest;
import com.epsglobal.services.datatransfer.permission.UpdatePermissionResponse;

@RestController
@RequestMapping("/api/v1/permissions")
@CrossOrigin(origins = "*")
public class PermissionController {
	@Autowired
	private PermissionApplicationService permissionApplicationService;

	@GetMapping
	public ResponseEntity<List<GetPermissionResponse>> findAll() {
		List<GetPermissionResponse> response = permissionApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetPermissionResponse> findById(@PathVariable Long id) {
		try {
			GetPermissionResponse response = permissionApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddPermissionResponse> add(@RequestBody AddPermissionRequest request) {
		try {
			AddPermissionResponse response = permissionApplicationService.add(request);
			return ResponseEntity.ok(response);
		}catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdatePermissionResponse> update(@PathVariable Long id, @RequestBody UpdatePermissionRequest request) {
		try {
			UpdatePermissionResponse response = permissionApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
