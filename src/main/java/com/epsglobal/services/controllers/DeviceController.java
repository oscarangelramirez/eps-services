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

import com.epsglobal.services.application.DeviceApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.device.AddDeviceRequest;
import com.epsglobal.services.datatransfer.device.AddDeviceResponse;
import com.epsglobal.services.datatransfer.device.GetDeviceResponse;
import com.epsglobal.services.datatransfer.device.UpdateDeviceRequest;
import com.epsglobal.services.datatransfer.device.UpdateDeviceResponse;

@RestController
@RequestMapping("/api/v1/devices")
@CrossOrigin(origins = "*")
public class DeviceController {
	@Autowired
	private DeviceApplicationService deviceApplicationService;

	@GetMapping
	public ResponseEntity<List<GetDeviceResponse>> findAll() {
		List<GetDeviceResponse> response = deviceApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetDeviceResponse> findById(@PathVariable Long id) {
		try {
			GetDeviceResponse response = deviceApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddDeviceResponse> add(@RequestBody AddDeviceRequest request) {
		try {
			AddDeviceResponse response = deviceApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateDeviceResponse> update(@PathVariable Long id, @RequestBody UpdateDeviceRequest request) {
		try {
			UpdateDeviceResponse response = deviceApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
