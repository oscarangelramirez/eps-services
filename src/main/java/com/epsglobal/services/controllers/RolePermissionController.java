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

import com.epsglobal.services.application.RolePermissionApplicationService;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.permission.GetPermissionResponse;
import com.epsglobal.services.datatransfer.role.permission.AddRolePermissionsRequest;
import com.epsglobal.services.datatransfer.role.permission.AddRolePermissionsResponse;
import com.epsglobal.services.datatransfer.role.permission.GetRolePermissionResponse;
import com.epsglobal.services.datatransfer.role.permission.UpdateRolePermissionRequest;
import com.epsglobal.services.datatransfer.role.permission.UpdateRolePermissionResponse;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "*")
public class RolePermissionController {
	@Autowired
	private RolePermissionApplicationService rolPermissionApplicationService;

	@GetMapping("{idRol}/permissions")
	public ResponseEntity<List<GetRolePermissionResponse>> findAll(@PathVariable Long idRol) {
		List<GetRolePermissionResponse> response = rolPermissionApplicationService.findAll(idRol);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idRol}/permissions")
	public ResponseEntity<AddRolePermissionsResponse> add(@PathVariable Long idRol, @RequestBody AddRolePermissionsRequest request) {
		try {
			AddRolePermissionsResponse response = rolPermissionApplicationService.add(idRol, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{idRol}/permissions/{id}")
	public ResponseEntity<UpdateRolePermissionResponse> update(@PathVariable Long idRol, @PathVariable Long id, @RequestBody UpdateRolePermissionRequest request) {
		try {
			UpdateRolePermissionResponse response = rolPermissionApplicationService.update(idRol, id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@GetMapping("{idRol}/permissions/not")
	public ResponseEntity<List<GetPermissionResponse>> findAllNotInRol(@PathVariable Long idRol) {
		try {
			List<GetPermissionResponse> response = rolPermissionApplicationService.findAllNotInRol(idRol);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
}
