package com.epsglobal.services.controllers;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epsglobal.services.application.ReportCarrierApplicationService;
import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.datatransfer.report.carrier.GenerateReportOfInputsRequest;

@RestController
@RequestMapping("/api/v1/reports/carriers")
@CrossOrigin(origins = "*")
public class ReportCarrierController {
	@Autowired
	private ReportCarrierApplicationService reportCarrierApplicationService;
	
	@PostMapping("inputs/download")
	public ResponseEntity<InputStreamResource> generateReportOfInputs(@RequestBody GenerateReportOfInputsRequest request) {
		try {
			ByteArrayInputStream byteArrayInputStream = reportCarrierApplicationService.generateReportOfInputs(request);
			
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Access-Control-Expose-Headers", "Content-Disposition");
		    headers.add("Content-Disposition", "attachment;filename=" + "Reporte entradas.xlsx");

			return ResponseEntity.ok()
		            .headers(headers)
		            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
		            .body(new InputStreamResource(byteArrayInputStream));
			
		} catch (ErrorException exception) {
			throw exception;
		}
	}
}
