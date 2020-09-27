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

import com.epsglobal.services.application.ManufacterApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.manufacter.AddManufacterRequest;
import com.epsglobal.services.datatransfer.manufacter.AddManufacterResponse;
import com.epsglobal.services.datatransfer.manufacter.GetManufacterResponse;
import com.epsglobal.services.datatransfer.manufacter.UpdateManufacterRequest;
import com.epsglobal.services.datatransfer.manufacter.UpdateManufacterResponse;

@RestController
@RequestMapping("/api/v1/manufacters")
@CrossOrigin(origins = "*")
public class ManufacterController {
	@Autowired
	private ManufacterApplicationService manufacterApplicationService;

	@GetMapping
	public ResponseEntity<List<GetManufacterResponse>> findAll() {
		List<GetManufacterResponse> response = manufacterApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetManufacterResponse> findById(@PathVariable Long id) {
		try {
			GetManufacterResponse response = manufacterApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddManufacterResponse> add(@RequestBody AddManufacterRequest request) {
		try {
			AddManufacterResponse response = manufacterApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateManufacterResponse> add(@PathVariable Long id, @RequestBody UpdateManufacterRequest request) {
		try {
			UpdateManufacterResponse response = manufacterApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
