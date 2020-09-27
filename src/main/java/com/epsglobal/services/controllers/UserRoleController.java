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

import com.epsglobal.services.application.UserRoleApplicationService;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.role.GetRoleResponse;
import com.epsglobal.services.datatransfer.user.role.AddUserRolesRequest;
import com.epsglobal.services.datatransfer.user.role.AddUserRolesResponse;
import com.epsglobal.services.datatransfer.user.role.DeleteUserRolesRequest;
import com.epsglobal.services.datatransfer.user.role.DeleteUserRolesResponse;
import com.epsglobal.services.datatransfer.user.role.GetUserRoleResponse;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserRoleController {
	@Autowired
	private UserRoleApplicationService userRolApplicationService;

	@GetMapping("{idUser}/roles")
	public ResponseEntity<List<GetUserRoleResponse>> findAll(@PathVariable Long idUser) {
		List<GetUserRoleResponse> response = userRolApplicationService.findAll(idUser);
		return ResponseEntity.ok(response);
	}

	@PostMapping("{idUser}/roles")
	public ResponseEntity<AddUserRolesResponse> add(@PathVariable Long idUser, @RequestBody AddUserRolesRequest request) {
		try {
			AddUserRolesResponse response = userRolApplicationService.add(idUser, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@DeleteMapping("{idUser}/roles")
	public ResponseEntity<DeleteUserRolesResponse> delete(@PathVariable Long idUser, @RequestBody DeleteUserRolesRequest request) {
		try {
			DeleteUserRolesResponse response = userRolApplicationService.delete(idUser, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@GetMapping("{idUser}/roles/not")
	public ResponseEntity<List<GetRoleResponse>> findAllNotInRol(@PathVariable Long idUser) {
		try {
			List<GetRoleResponse> response = userRolApplicationService.findAllNotInUser(idUser);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
}
