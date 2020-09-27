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

import com.epsglobal.services.application.RoleApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.role.AddRoleRequest;
import com.epsglobal.services.datatransfer.role.AddRoleResponse;
import com.epsglobal.services.datatransfer.role.GetRoleResponse;
import com.epsglobal.services.datatransfer.role.UpdateRoleRequest;
import com.epsglobal.services.datatransfer.role.UpdateRoleResponse;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "*")
public class RoleController {
	@Autowired
	private RoleApplicationService rolApplicationService;

	@GetMapping
	public ResponseEntity<List<GetRoleResponse>> findAll() {
		List<GetRoleResponse> response = rolApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetRoleResponse> findById(@PathVariable Long id) {
		try {
			GetRoleResponse response = rolApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddRoleResponse> add(@RequestBody AddRoleRequest request) {
		try {
			AddRoleResponse response = rolApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateRoleResponse> update(@PathVariable Long id, @RequestBody UpdateRoleRequest request) {
		try {
			UpdateRoleResponse response = rolApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
