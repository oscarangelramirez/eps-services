package com.epsglobal.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epsglobal.services.application.ValidationApplicationService;
import com.epsglobal.services.datatransfer.validation.ValidationManufacterPartNumberRequest;
import com.epsglobal.services.datatransfer.validation.ValidationResponse;

@RestController
@RequestMapping("/api/v1/validations")
@CrossOrigin(origins = "*")
public class ValidationController {
	@Autowired
	private ValidationApplicationService validationApplicationService;
	
	@PostMapping("validatemanufacterpartnumber")
	public ResponseEntity<ValidationResponse> validateManufacterPartNumber(@RequestBody ValidationManufacterPartNumberRequest request) {
		ValidationResponse response = validationApplicationService.validateManufacterPartNumber(request);
		return ResponseEntity.ok(response);
	}
}
