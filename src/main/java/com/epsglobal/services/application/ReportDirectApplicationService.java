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
import com.epsglobal.services.dataaccess.WarehouseDirectInputRepository;
import com.epsglobal.services.dataaccess.WarehouseDirectOutputRepository;
import com.epsglobal.services.dataaccess.WarehouseDirectTransferRepository;
import com.epsglobal.services.dataaccess.specifications.WarehouseDirectInputSpecification;
import com.epsglobal.services.dataaccess.specifications.WarehouseDirectOutputSpecification;
import com.epsglobal.services.dataaccess.specifications.WarehouseDirectTransferSpecification;
import com.epsglobal.services.datatransfer.report.direct.GenerateReportOfInputsRequest;
import com.epsglobal.services.datatransfer.report.direct.GenerateReportOfOutputsRequest;
import com.epsglobal.services.datatransfer.report.direct.GenerateReportOfTransfersRequest;
import com.epsglobal.services.domain.WarehouseDirectInput;
import com.epsglobal.services.domain.WarehouseDirectOutput;
import com.epsglobal.services.domain.WarehouseDirectTransfer;

@Service
public class ReportDirectApplicationService {
	@Autowired
    private WarehouseDirectInputRepository warehouseDirectInputRepository;
	
	@Autowired
    private WarehouseDirectOutputRepository warehouseDirectOutputRepository;
	
	@Autowired
    private WarehouseDirectTransferRepository warehouseDirectTransferRepository;
	
	public ByteArrayInputStream generateReportOfInputs(GenerateReportOfInputsRequest request) throws ErrorException {
		String[] headers = { "Almacen", "PN", "Fecha", "Cantidad", "Comentarios" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Specification<WarehouseDirectInput> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseDirectInputSpecification.getByWarehouse(request.getIdWarehouse()));
		}
		if(request.getFromDate() != null && request.getToDate() != null)
		{
			specification = specification.and(WarehouseDirectInputSpecification.getByDate(request.getFromDate(), request.getToDate()));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseDirectInputSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseDirectInput> warehouseDirectInputs = warehouseDirectInputRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Entradas");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseDirectInput warehouseDirectInput : warehouseDirectInputs) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseDirectInput.getWarehouseDirect().getWarehouse().getName());
		        row.createCell(1).setCellValue(warehouseDirectInput.getWarehouseDirect().getDirect().getManufacterPartNumber());
		        row.createCell(2).setCellValue(simpleDateFormat.format(warehouseDirectInput.getDate()));
		        row.createCell(3).setCellValue(warehouseDirectInput.getQuantity() != null ? warehouseDirectInput.getQuantity() : 0);
		        row.createCell(4).setCellValue(warehouseDirectInput.getComments() != null ? warehouseDirectInput.getComments() : "");
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
		      throw new ErrorException(0, "Error generating the report");
	    }
	}
	
	public ByteArrayInputStream generateReportOfOutputs(GenerateReportOfOutputsRequest request) throws ErrorException {
		String[] headers = { "Almacen", "PN", "Fecha", "Cantidad", "Comentarios" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Specification<WarehouseDirectOutput> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseDirectOutputSpecification.getByWarehouse(request.getIdWarehouse()));
		}
		if(request.getInitialDate() != null && request.getFinalDate() != null)
		{
			specification = specification.and(WarehouseDirectOutputSpecification.getByDate(request.getInitialDate(), request.getFinalDate()));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseDirectOutputSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseDirectOutput> warehouseDirectOutputs = warehouseDirectOutputRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Salidas");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseDirectOutput warehouseDirectOutput : warehouseDirectOutputs) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseDirectOutput.getWarehouseDirect().getWarehouse().getName());
		        row.createCell(1).setCellValue(warehouseDirectOutput.getWarehouseDirect().getDirect().getManufacterPartNumber());
		        row.createCell(2).setCellValue(simpleDateFormat.format(warehouseDirectOutput.getDate()));
		        row.createCell(3).setCellValue(warehouseDirectOutput.getQuantity() != null ? warehouseDirectOutput.getQuantity(): 0);
		        row.createCell(4).setCellValue(warehouseDirectOutput.getComments() != null ? warehouseDirectOutput.getComments() : "");
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
			throw new ErrorException(0, "Error generating the report");
	    }
	}
	
	public ByteArrayInputStream generateReportOfTransfers(GenerateReportOfTransfersRequest request) throws ErrorException {
		String[] headers = { "Almacen Origen", "Almacen Destino", "Estado", "PN", "Fecha Origen", "Fecha Destino", "Cantidad Origen", "Cantidad Destino", "Comentarios Origen", "Comentarios Destino" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Specification<WarehouseDirectTransfer> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseDirectTransferSpecification.getByWarehouseOrigin(request.getIdWarehouse())
					.or(WarehouseDirectTransferSpecification.getByWarehouseDestination(request.getIdWarehouse())));
		}
		if(request.getInitialDate() != null && request.getFinalDate() != null)
		{
			specification = specification.and(WarehouseDirectTransferSpecification.getByDateOrigin(request.getInitialDate(), request.getFinalDate())
					.or(WarehouseDirectTransferSpecification.getByDateDestination(request.getInitialDate(), request.getFinalDate())));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseDirectTransferSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseDirectTransfer> warehouseDirectTransfers = warehouseDirectTransferRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Transferencias");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseDirectTransfer warehouseDirectTransfer : warehouseDirectTransfers) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseDirectTransfer.getOrigin().getWarehouse().getName());
				if(warehouseDirectTransfer.getDestination() != null) {
					row.createCell(1).setCellValue(warehouseDirectTransfer.getDestination().getWarehouse().getName());
				}
				
				switch (warehouseDirectTransfer.getStatus()) {
				case SENT:
					row.createCell(2).setCellValue("ENVIADO");
					break;
				case RECEIVED:
					row.createCell(2).setCellValue("RECIBIDO");
					break;
				case CANCELLED:
					row.createCell(2).setCellValue("CANCELADO");
					break;
				}
				
		        row.createCell(3).setCellValue(warehouseDirectTransfer.getOrigin().getDirect().getManufacterPartNumber());
		        
		        row.createCell(4).setCellValue(simpleDateFormat.format(warehouseDirectTransfer.getOrigin().getDate()));
		        if(warehouseDirectTransfer.getDestination() != null) {
					row.createCell(5).setCellValue(warehouseDirectTransfer.getDestination().getDate() != null ? simpleDateFormat.format(warehouseDirectTransfer.getDestination().getDate()) : "");
				}
		        
		        row.createCell(6).setCellValue(warehouseDirectTransfer.getOrigin().getQuantity() != null ? warehouseDirectTransfer.getOrigin().getQuantity() : 0);
		        if(warehouseDirectTransfer.getDestination() != null) {
					row.createCell(7).setCellValue(warehouseDirectTransfer.getDestination().getQuantity() != null ? warehouseDirectTransfer.getDestination().getQuantity() : 0);
				}
		        
		        row.createCell(8).setCellValue(warehouseDirectTransfer.getOrigin().getComments() != null ? warehouseDirectTransfer.getOrigin().getComments() : "");
		        if(warehouseDirectTransfer.getDestination() != null) {
		        	row.createCell(9).setCellValue(warehouseDirectTransfer.getDestination().getComments() != null ? warehouseDirectTransfer.getDestination().getComments() : "");
				}
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
			throw new ErrorException(0, "Error generating the report");
	    }
	}
}