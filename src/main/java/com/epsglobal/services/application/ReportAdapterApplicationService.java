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
import com.epsglobal.services.dataaccess.WarehouseAdapterInputRepository;
import com.epsglobal.services.dataaccess.WarehouseAdapterOutputRepository;
import com.epsglobal.services.dataaccess.WarehouseAdapterTransferRepository;
import com.epsglobal.services.dataaccess.specifications.WarehouseAdapterInputSpecification;
import com.epsglobal.services.dataaccess.specifications.WarehouseAdapterOutputSpecification;
import com.epsglobal.services.dataaccess.specifications.WarehouseAdapterTransferSpecification;
import com.epsglobal.services.datatransfer.report.adapter.GenerateReportOfInputsRequest;
import com.epsglobal.services.datatransfer.report.adapter.GenerateReportOfOutputsRequest;
import com.epsglobal.services.datatransfer.report.adapter.GenerateReportOfTransfersRequest;
import com.epsglobal.services.domain.WarehouseAdapterInput;
import com.epsglobal.services.domain.WarehouseAdapterOutput;
import com.epsglobal.services.domain.WarehouseAdapterTransfer;

@Service
public class ReportAdapterApplicationService {
	@Autowired
    private WarehouseAdapterInputRepository warehouseAdapterInputRepository;
	
	@Autowired
    private WarehouseAdapterOutputRepository warehouseAdapterOutputRepository;
	
	@Autowired
    private WarehouseAdapterTransferRepository warehouseAdapterTransferRepository;
	
	public ByteArrayInputStream generateReportOfInputs(GenerateReportOfInputsRequest request) throws ErrorException {
		String[] headers = { "Almacen", "PN", "Fecha", "Cantidad", "Comentarios" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Specification<WarehouseAdapterInput> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseAdapterInputSpecification.getByWarehouse(request.getIdWarehouse()));
		}
		if(request.getFromDate() != null && request.getToDate() != null)
		{
			specification = specification.and(WarehouseAdapterInputSpecification.getByDate(request.getFromDate(), request.getToDate()));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseAdapterInputSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseAdapterInput> warehouseAdapterInputs = warehouseAdapterInputRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Entradas");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseAdapterInput warehouseAdapterInput : warehouseAdapterInputs) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseAdapterInput.getWarehouseAdapter().getWarehouse().getName());
		        row.createCell(1).setCellValue(warehouseAdapterInput.getWarehouseAdapter().getAdapter().getManufacterPartNumber());
		        row.createCell(2).setCellValue(simpleDateFormat.format(warehouseAdapterInput.getDate()));
		        row.createCell(3).setCellValue(warehouseAdapterInput.getQuantity() != null ? warehouseAdapterInput.getQuantity() : 0);
		        row.createCell(4).setCellValue(warehouseAdapterInput.getComments() != null ? warehouseAdapterInput.getComments() : "");
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
		Specification<WarehouseAdapterOutput> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseAdapterOutputSpecification.getByWarehouse(request.getIdWarehouse()));
		}
		if(request.getInitialDate() != null && request.getFinalDate() != null)
		{
			specification = specification.and(WarehouseAdapterOutputSpecification.getByDate(request.getInitialDate(), request.getFinalDate()));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseAdapterOutputSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseAdapterOutput> warehouseAdapterOutputs = warehouseAdapterOutputRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Salidas");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseAdapterOutput warehouseAdapterOutput : warehouseAdapterOutputs) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseAdapterOutput.getWarehouseAdapter().getWarehouse().getName());
		        row.createCell(1).setCellValue(warehouseAdapterOutput.getWarehouseAdapter().getAdapter().getManufacterPartNumber());
		        row.createCell(2).setCellValue(simpleDateFormat.format(warehouseAdapterOutput.getDate()));
		        row.createCell(3).setCellValue(warehouseAdapterOutput.getQuantity() != null ? warehouseAdapterOutput.getQuantity(): 0);
		        row.createCell(5).setCellValue(warehouseAdapterOutput.getComments() != null ? warehouseAdapterOutput.getComments() : "");
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
		Specification<WarehouseAdapterTransfer> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseAdapterTransferSpecification.getByWarehouseOrigin(request.getIdWarehouse())
					.or(WarehouseAdapterTransferSpecification.getByWarehouseDestination(request.getIdWarehouse())));
		}
		if(request.getInitialDate() != null && request.getFinalDate() != null)
		{
			specification = specification.and(WarehouseAdapterTransferSpecification.getByDateOrigin(request.getInitialDate(), request.getFinalDate())
					.or(WarehouseAdapterTransferSpecification.getByDateDestination(request.getInitialDate(), request.getFinalDate())));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseAdapterTransferSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseAdapterTransfer> warehouseAdapterTransfers = warehouseAdapterTransferRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Transferencias");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseAdapterTransfer warehouseAdapterTransfer : warehouseAdapterTransfers) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseAdapterTransfer.getOrigin().getWarehouse().getName());
				if(warehouseAdapterTransfer.getDestination() != null) {
					row.createCell(1).setCellValue(warehouseAdapterTransfer.getDestination().getWarehouse().getName());
				}
				
				switch (warehouseAdapterTransfer.getStatus()) {
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
				
		        row.createCell(3).setCellValue(warehouseAdapterTransfer.getOrigin().getAdapter().getManufacterPartNumber());
		        
		        row.createCell(4).setCellValue(simpleDateFormat.format(warehouseAdapterTransfer.getOrigin().getDate()));
		        if(warehouseAdapterTransfer.getDestination() != null) {
					row.createCell(5).setCellValue(warehouseAdapterTransfer.getDestination().getDate() != null ? simpleDateFormat.format(warehouseAdapterTransfer.getDestination().getDate()) : "");
				}
		        
		        row.createCell(6).setCellValue(warehouseAdapterTransfer.getOrigin().getQuantity() != null ? warehouseAdapterTransfer.getOrigin().getQuantity() : 0);
		        if(warehouseAdapterTransfer.getDestination() != null) {
					row.createCell(7).setCellValue(warehouseAdapterTransfer.getDestination().getQuantity() != null ? warehouseAdapterTransfer.getDestination().getQuantity() : 0);
				}
		        
		        row.createCell(8).setCellValue(warehouseAdapterTransfer.getOrigin().getComments() != null ? warehouseAdapterTransfer.getOrigin().getComments() : "");
		        if(warehouseAdapterTransfer.getDestination() != null) {
		        	row.createCell(9).setCellValue(warehouseAdapterTransfer.getDestination().getComments() != null ? warehouseAdapterTransfer.getDestination().getComments() : "");
				}
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
			throw new ErrorException(0, "Error generating the report");
	    }
	}
}