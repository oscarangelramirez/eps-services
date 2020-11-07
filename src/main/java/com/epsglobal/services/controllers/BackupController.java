package com.epsglobal.services.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epsglobal.services.application.BackupApplicationService;
import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.datatransfer.backup.AddBackupRequest;
import com.epsglobal.services.datatransfer.backup.AddBackupResponse;
import com.epsglobal.services.datatransfer.backup.DownloadBackupResponse;
import com.epsglobal.services.datatransfer.backup.GetBackupResponse;

@RestController
@RequestMapping("/api/v1/backups")
@CrossOrigin(origins = "*")
public class BackupController {

	@Autowired
	private BackupApplicationService backupApplicationService;

	@GetMapping
	public ResponseEntity<List<GetBackupResponse>> findAll() {
		List<GetBackupResponse> response = backupApplicationService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("{id}")
	public ResponseEntity<GetBackupResponse> findById(@PathVariable Long id) {
		try {
			GetBackupResponse response = backupApplicationService.findById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException exception) {
			throw exception;
		}
	}
	
	@GetMapping("{id}/download")
	public ResponseEntity<InputStreamResource> download(@PathVariable Long id) {
		try {
			DownloadBackupResponse response = backupApplicationService.Download(id);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.getBytes());
			
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Access-Control-Expose-Headers", "Content-Disposition");
		    headers.add("Content-Disposition", "attachment;filename=" + response.getName());

			return ResponseEntity.ok()
		            .headers(headers)
		            .contentType(MediaType.APPLICATION_OCTET_STREAM)
		            .body(new InputStreamResource(byteArrayInputStream));
			
		} catch (NotFoundException exception) {
			throw exception;
		} catch (ErrorException exception) {
			throw exception;
		}
	}
	
	@PostMapping
	public ResponseEntity<AddBackupResponse> add(@RequestBody AddBackupRequest request) {
		try {
			AddBackupResponse response = backupApplicationService.add(request);
			return ResponseEntity.ok(response);
		} catch (ErrorException exception) {
			throw exception;
		}
	}
}
