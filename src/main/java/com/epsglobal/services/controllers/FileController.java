package com.epsglobal.services.controllers;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.epsglobal.services.application.FileApplicationService;
import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.file.AddFileResponse;
import com.epsglobal.services.datatransfer.file.GetFileResponse;
import com.epsglobal.services.datatransfer.file.UpdateFileResponse;

@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(origins = "*")
public class FileController {
	@Autowired
	private FileApplicationService fileApplicationService;
	
	@GetMapping("{id}")
	public ResponseEntity<InputStreamResource> get(@PathVariable Long id) {
		try {
			GetFileResponse response = fileApplicationService.get(id);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.getBytes());
			
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Access-Control-Expose-Headers", "Content-Disposition");
		    headers.add("Content-Disposition", "inline;filename=" + response.getName());

			return ResponseEntity.ok()
		            .headers(headers)
		            .contentType(MediaType.APPLICATION_PDF)
		            .body(new InputStreamResource(byteArrayInputStream));
			
		} catch (NotFoundException exception) {
			throw exception;
		}catch (ErrorException exception) {
			throw exception;
		}
	}
	
	@PostMapping
	public ResponseEntity<AddFileResponse> add(@RequestParam("file") MultipartFile request) {
		try {
			AddFileResponse response = fileApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (ErrorException exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UpdateFileResponse> update(@PathVariable Long id, @RequestParam("file") MultipartFile request) {
		try {
			UpdateFileResponse response = fileApplicationService.update(id, request);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (ErrorException exception) {
			throw exception;
		}
	}
}
