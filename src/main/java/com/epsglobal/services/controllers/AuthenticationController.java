package com.epsglobal.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epsglobal.services.application.AuthenticationApplicationService;
import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.authentication.LoginUserRequest;
import com.epsglobal.services.datatransfer.authentication.LoginUserResponse;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin(origins = "*")
public class AuthenticationController {
	@Autowired
	private AuthenticationApplicationService authenticationApplicationService;

	@PostMapping("login")
	public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest request) {
		try {
			LoginUserResponse response = authenticationApplicationService.login(request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		} catch (ErrorException exception) {
			throw exception;
		}
	}
}
