package com.epsglobal.services.datatransfer.report.direct;

import java.util.Date;

import lombok.Data;

@Data
public class GenerateReportOfTransfersRequest {
	private Long idWarehouse;
	private Date initialDate;
	private Date finalDate;
	private String manufacterPartNumber;
}
