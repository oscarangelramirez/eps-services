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

import com.epsglobal.services.application.UserApplicationService;
import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.user.AddUserRequest;
import com.epsglobal.services.datatransfer.user.AddUserResponse;
import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.datatransfer.user.UpdateUserRequest;
import com.epsglobal.services.datatransfer.user.UpdateUserResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserApplicationService userApplicationService;

	@GetMapping
	public ResponseEntity<List<GetUserResponse>> findAll() {
		List<GetUserResponse> response = userApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetUserResponse> findById(@PathVariable Long id) {
		try {
			GetUserResponse response = userApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddUserResponse> add(@RequestBody AddUserRequest request) {
		try {
			AddUserResponse response = userApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		} catch (ErrorException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateUserResponse> update(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
		try {
			UpdateUserResponse response = userApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@GetMapping("{id}/warehouses")
	public ResponseEntity<List<GetWarehouseResponse>> findAllWarehousesByUser(@PathVariable Long id) {
		try {
			List<GetWarehouseResponse> response = userApplicationService.findAllWarehousesByUser(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
}
