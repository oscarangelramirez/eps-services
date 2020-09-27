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

import com.epsglobal.services.application.DirectApplicationService;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.direct.AddDirectRequest;
import com.epsglobal.services.datatransfer.direct.AddDirectResponse;
import com.epsglobal.services.datatransfer.direct.GetDirectResponse;
import com.epsglobal.services.datatransfer.direct.UpdateDirectRequest;
import com.epsglobal.services.datatransfer.direct.UpdateDirectResponse;

@RestController
@RequestMapping("/api/v1/directs")
@CrossOrigin(origins = "*")
public class DirectController {
	@Autowired
	private DirectApplicationService directApplicationService;

	@GetMapping
	public ResponseEntity<List<GetDirectResponse>> findAll() {
		List<GetDirectResponse> response = directApplicationService.findAll();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<GetDirectResponse> findById(@PathVariable Long id) {
		try {
			GetDirectResponse response = directApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<AddDirectResponse> add(@RequestBody AddDirectRequest request) {
		try {
			AddDirectResponse response = directApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (InvalidException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateDirectResponse> update(@PathVariable Long id, @RequestBody UpdateDirectRequest request) {
		try {
			UpdateDirectResponse response = directApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (InvalidException exception) {
			throw exception;
		}
	}
}
