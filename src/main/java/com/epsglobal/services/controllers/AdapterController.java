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

import com.epsglobal.services.application.AdapterApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.adapter.AddAdapterRequest;
import com.epsglobal.services.datatransfer.adapter.AddAdapterResponse;
import com.epsglobal.services.datatransfer.adapter.GetAdapterResponse;
import com.epsglobal.services.datatransfer.adapter.UpdateAdapterRequest;
import com.epsglobal.services.datatransfer.adapter.UpdateAdapterResponse;

@RestController
@RequestMapping("/api/v1/adapters")
@CrossOrigin(origins = "*")
public class AdapterController {
	@Autowired
	private AdapterApplicationService adapterApplicationService;

	@GetMapping
	public ResponseEntity<List<GetAdapterResponse>> findAll() {
		List<GetAdapterResponse> response = adapterApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetAdapterResponse> findById(@PathVariable Long id) {
		try {
			GetAdapterResponse response = adapterApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddAdapterResponse> add(@RequestBody AddAdapterRequest request) {
		try {
			AddAdapterResponse response = adapterApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateAdapterResponse> update(@PathVariable Long id, @RequestBody UpdateAdapterRequest request) {
		try {
			UpdateAdapterResponse response = adapterApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
