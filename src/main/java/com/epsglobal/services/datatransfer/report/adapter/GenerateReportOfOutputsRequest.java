package com.epsglobal.services.datatransfer.report.adapter;

import java.util.Date;

import lombok.Data;

@Data
public class GenerateReportOfOutputsRequest {
	private Long idWarehouse;
	private Date initialDate;
	private Date finalDate;
	private String manufacterPartNumber;
}
