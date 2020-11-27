package com.epsglobal.services.datatransfer.report.device;

import java.util.Date;

import lombok.Data;

@Data
public class GenerateReportOfInputsRequest {
	private Long idWarehouse;
	private Date fromDate;
	private Date toDate;
	private String manufacterPartNumber;
}
