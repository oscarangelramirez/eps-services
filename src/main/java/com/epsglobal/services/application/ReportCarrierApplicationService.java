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
import com.epsglobal.services.dataaccess.WarehouseCarrierOutputRepository;
import com.epsglobal.services.dataaccess.WarehouseCarrierTransferRepository;
import com.epsglobal.services.dataaccess.specifications.WarehouseCarrierInputSpecification;
import com.epsglobal.services.dataaccess.specifications.WarehouseCarrierOutputSpecification;
import com.epsglobal.services.dataaccess.specifications.WarehouseCarrierTransferSpecification;
import com.epsglobal.services.datatransfer.report.carrier.GenerateReportOfInputsRequest;
import com.epsglobal.services.datatransfer.report.carrier.GenerateReportOfOutputsRequest;
import com.epsglobal.services.datatransfer.report.carrier.GenerateReportOfTransfersRequest;
import com.epsglobal.services.domain.WarehouseCarrierInput;
import com.epsglobal.services.domain.WarehouseCarrierOutput;
import com.epsglobal.services.domain.WarehouseCarrierTransfer;

@Service
public class ReportCarrierApplicationService {
	@Autowired
    private WarehouseCarrierInputRepository warehouseCarrierInputRepository;
	
	@Autowired
    private WarehouseCarrierOutputRepository warehouseCarrierOutputRepository;
	
	@Autowired
    private WarehouseCarrierTransferRepository warehouseCarrierTransferRepository;
	
	public ByteArrayInputStream generateReportOfInputs(GenerateReportOfInputsRequest request) throws ErrorException {
		String[] headers = { "Almacen", "PN", "Fecha", "Completos", "Parciales", "Comentarios" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Specification<WarehouseCarrierInput> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseCarrierInputSpecification.getByWarehouse(request.getIdWarehouse()));
		}
		if(request.getFromDate() != null && request.getToDate() != null)
		{
			specification = specification.and(WarehouseCarrierInputSpecification.getByDate(request.getFromDate(), request.getToDate()));
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
		        row.createCell(3).setCellValue(warehouseCarrierInput.getComplete() != null ? warehouseCarrierInput.getComplete() : 0);
		        row.createCell(4).setCellValue(warehouseCarrierInput.getPartial() != null ? warehouseCarrierInput.getPartial() : 0);
		        row.createCell(5).setCellValue(warehouseCarrierInput.getComments() != null ? warehouseCarrierInput.getComments() : "");
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
		      throw new ErrorException(0, "Error generating the report");
	    }
	}
	
	public ByteArrayInputStream generateReportOfOutputs(GenerateReportOfOutputsRequest request) throws ErrorException {
		String[] headers = { "Almacen", "PN", "Fecha", "Completos", "Parciales", "Comentarios" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Specification<WarehouseCarrierOutput> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseCarrierOutputSpecification.getByWarehouse(request.getIdWarehouse()));
		}
		if(request.getInitialDate() != null && request.getFinalDate() != null)
		{
			specification = specification.and(WarehouseCarrierOutputSpecification.getByDate(request.getInitialDate(), request.getFinalDate()));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseCarrierOutputSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseCarrierOutput> warehouseCarrierOutputs = warehouseCarrierOutputRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Salidas");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseCarrierOutput warehouseCarrierOutput : warehouseCarrierOutputs) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseCarrierOutput.getWarehouseCarrier().getWarehouse().getName());
		        row.createCell(1).setCellValue(warehouseCarrierOutput.getWarehouseCarrier().getCarrier().getManufacterPartNumber());
		        row.createCell(2).setCellValue(simpleDateFormat.format(warehouseCarrierOutput.getDate()));
		        row.createCell(3).setCellValue(warehouseCarrierOutput.getComplete() != null ? warehouseCarrierOutput.getComplete(): 0);
		        row.createCell(4).setCellValue(warehouseCarrierOutput.getPartial() != null  ? warehouseCarrierOutput.getPartial() : 0);
		        row.createCell(5).setCellValue(warehouseCarrierOutput.getComments() != null ? warehouseCarrierOutput.getComments() : "");
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
			throw new ErrorException(0, "Error generating the report");
	    }
	}
	
	public ByteArrayInputStream generateReportOfTransfers(GenerateReportOfTransfersRequest request) throws ErrorException {
		String[] headers = { "Almacen Origen", "Almacen Destino", "Estado", "PN", "Fecha Origen", "Fecha Destino", "Completos Origen", "Completos Destino", "Parciales Origen", "Parciales Destino", "Comentarios Origen", "Comentarios Destino" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Specification<WarehouseCarrierTransfer> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseCarrierTransferSpecification.getByWarehouseOrigin(request.getIdWarehouse())
					.or(WarehouseCarrierTransferSpecification.getByWarehouseDestination(request.getIdWarehouse())));
		}
		if(request.getInitialDate() != null && request.getFinalDate() != null)
		{
			specification = specification.and(WarehouseCarrierTransferSpecification.getByDateOrigin(request.getInitialDate(), request.getFinalDate())
					.or(WarehouseCarrierTransferSpecification.getByDateDestination(request.getInitialDate(), request.getFinalDate())));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseCarrierTransferSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseCarrierTransfer> warehouseCarrierTransfers = warehouseCarrierTransferRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Transferencias");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseCarrierTransfer warehouseCarrierTransfer : warehouseCarrierTransfers) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseCarrierTransfer.getOrigin().getWarehouse().getName());
				if(warehouseCarrierTransfer.getDestination() != null) {
					row.createCell(1).setCellValue(warehouseCarrierTransfer.getDestination().getWarehouse().getName());
				}
				
				switch (warehouseCarrierTransfer.getStatus()) {
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
				
		        row.createCell(3).setCellValue(warehouseCarrierTransfer.getOrigin().getCarrier().getManufacterPartNumber());
		        
		        row.createCell(4).setCellValue(simpleDateFormat.format(warehouseCarrierTransfer.getOrigin().getDate()));
		        if(warehouseCarrierTransfer.getDestination() != null) {
					row.createCell(5).setCellValue(warehouseCarrierTransfer.getDestination().getDate() != null ? simpleDateFormat.format(warehouseCarrierTransfer.getDestination().getDate()) : "");
				}
		        
		        row.createCell(6).setCellValue(warehouseCarrierTransfer.getOrigin().getComplete() != null ? warehouseCarrierTransfer.getOrigin().getComplete() : 0);
		        if(warehouseCarrierTransfer.getDestination() != null) {
					row.createCell(7).setCellValue(warehouseCarrierTransfer.getDestination().getComplete() != null ? warehouseCarrierTransfer.getDestination().getComplete() : 0);
				}
		        
		        row.createCell(8).setCellValue(warehouseCarrierTransfer.getOrigin().getPartial() != null ? warehouseCarrierTransfer.getOrigin().getPartial() : 0);
		        if(warehouseCarrierTransfer.getDestination() != null) {
		        	row.createCell(9).setCellValue(warehouseCarrierTransfer.getDestination().getPartial() != null ? warehouseCarrierTransfer.getDestination().getPartial() : 0);
				}
		        
		        row.createCell(10).setCellValue(warehouseCarrierTransfer.getOrigin().getComments() != null ? warehouseCarrierTransfer.getOrigin().getComments() : "");
		        if(warehouseCarrierTransfer.getDestination() != null) {
		        	row.createCell(11).setCellValue(warehouseCarrierTransfer.getDestination().getComments() != null ? warehouseCarrierTransfer.getDestination().getComments() : "");
				}
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new ErrorException(0, "Error generating the report");
	    }
	}
}