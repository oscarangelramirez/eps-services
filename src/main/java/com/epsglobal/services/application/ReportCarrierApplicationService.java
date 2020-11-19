package com.epsglobal.services.application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.dataaccess.WarehouseCarrierInputRepository;
import com.epsglobal.services.dataaccess.specifications.WarehouseCarrierInputSpecification;
import com.epsglobal.services.datatransfer.report.carrier.GenerateReportOfInputsRequest;
import com.epsglobal.services.domain.WarehouseCarrierInput;

@Service
public class ReportCarrierApplicationService {
	@Autowired
    private WarehouseCarrierInputRepository warehouseCarrierInputRepository;
	
	public ByteArrayInputStream generateReportOfInputs(GenerateReportOfInputsRequest request) throws ErrorException {
		String[] headers = { "Almacen", "PN", "Fecha", "Completos", "Parciales", "Comentarios" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Specification<WarehouseCarrierInput> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseCarrierInputSpecification.getByWarehouse(request.getIdWarehouse()));
		}
		if(request.getInitialDate() != null && request.getFinalDate() != null)
		{
			specification = specification.and(WarehouseCarrierInputSpecification.getByDate(request.getInitialDate(), request.getFinalDate()));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseCarrierInputSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseCarrierInput> warehouseCarrierInputs = warehouseCarrierInputRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Entradas");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseCarrierInput warehouseCarrierInput : warehouseCarrierInputs) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseCarrierInput.getWarehouseCarrier().getWarehouse().getName());
		        row.createCell(1).setCellValue(warehouseCarrierInput.getWarehouseCarrier().getCarrier().getManufacterPartNumber());
		        row.createCell(2).setCellValue(simpleDateFormat.format(warehouseCarrierInput.getDate()));
		        row.createCell(3).setCellValue(warehouseCarrierInput.getComplete());
		        row.createCell(4).setCellValue(warehouseCarrierInput.getPartial());
		        row.createCell(5).setCellValue(warehouseCarrierInput.getComments());
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
		      throw new ErrorException(0, "Error generating the report");
	    }
	}
}