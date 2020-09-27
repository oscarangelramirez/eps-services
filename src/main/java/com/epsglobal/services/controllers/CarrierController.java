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

import com.epsglobal.services.application.CarrierApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.carrier.AddCarrierRequest;
import com.epsglobal.services.datatransfer.carrier.AddCarrierResponse;
import com.epsglobal.services.datatransfer.carrier.GetCarrierResponse;
import com.epsglobal.services.datatransfer.carrier.UpdateCarrierRequest;
import com.epsglobal.services.datatransfer.carrier.UpdateCarrierResponse;

@RestController
@RequestMapping("/api/v1/carriers")
@CrossOrigin(origins = "*")
public class CarrierController {
	@Autowired
	private CarrierApplicationService carrierApplicationService;

	@GetMapping
	public ResponseEntity<List<GetCarrierResponse>> findAll() {
		List<GetCarrierResponse> response = carrierApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetCarrierResponse> findById(@PathVariable Long id) {
		try {
			GetCarrierResponse response = carrierApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddCarrierResponse> add(@RequestBody AddCarrierRequest request) {
		try {
			AddCarrierResponse response = carrierApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateCarrierResponse> update(@PathVariable Long id, @RequestBody UpdateCarrierRequest request) {
		try {
			UpdateCarrierResponse response = carrierApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
