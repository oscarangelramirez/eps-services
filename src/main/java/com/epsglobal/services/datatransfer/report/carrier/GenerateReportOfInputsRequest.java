package com.epsglobal.services.datatransfer.report.carrier;

import java.util.Date;

import lombok.Data;

@Data
public class GenerateReportOfInputsRequest {
	private Long idWarehouse;
	private Date initialDate;
	private Date finalDate;
	private String manufacterPartNumber;
}
