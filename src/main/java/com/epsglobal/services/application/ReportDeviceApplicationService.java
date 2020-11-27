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
import com.epsglobal.services.dataaccess.WarehouseDeviceInputRepository;
import com.epsglobal.services.dataaccess.WarehouseDeviceOutputRepository;
import com.epsglobal.services.dataaccess.WarehouseDeviceTransferRepository;
import com.epsglobal.services.dataaccess.specifications.WarehouseDeviceInputSpecification;
import com.epsglobal.services.dataaccess.specifications.WarehouseDeviceOutputSpecification;
import com.epsglobal.services.dataaccess.specifications.WarehouseDeviceTransferSpecification;
import com.epsglobal.services.datatransfer.report.device.GenerateReportOfInputsRequest;
import com.epsglobal.services.datatransfer.report.device.GenerateReportOfOutputsRequest;
import com.epsglobal.services.datatransfer.report.device.GenerateReportOfTransfersRequest;
import com.epsglobal.services.domain.WarehouseDeviceInput;
import com.epsglobal.services.domain.WarehouseDeviceOutput;
import com.epsglobal.services.domain.WarehouseDeviceTransfer;

@Service
public class ReportDeviceApplicationService {
	@Autowired
    private WarehouseDeviceInputRepository warehouseDeviceInputRepository;
	
	@Autowired
    private WarehouseDeviceOutputRepository warehouseDeviceOutputRepository;
	
	@Autowired
    private WarehouseDeviceTransferRepository warehouseDeviceTransferRepository;
	
	public ByteArrayInputStream generateReportOfInputs(GenerateReportOfInputsRequest request) throws ErrorException {
		String[] headers = { "Almacen", "PN", "Fecha", "Cantidad", "Comentarios" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Specification<WarehouseDeviceInput> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseDeviceInputSpecification.getByWarehouse(request.getIdWarehouse()));
		}
		if(request.getFromDate() != null && request.getToDate() != null)
		{
			specification = specification.and(WarehouseDeviceInputSpecification.getByDate(request.getFromDate(), request.getToDate()));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseDeviceInputSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseDeviceInput> warehouseDeviceInputs = warehouseDeviceInputRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Entradas");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseDeviceInput warehouseDeviceInput : warehouseDeviceInputs) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseDeviceInput.getWarehouseDevice().getWarehouse().getName());
		        row.createCell(1).setCellValue(warehouseDeviceInput.getWarehouseDevice().getDevice().getManufacterPartNumber());
		        row.createCell(2).setCellValue(simpleDateFormat.format(warehouseDeviceInput.getDate()));
		        row.createCell(3).setCellValue(warehouseDeviceInput.getQuantity() != null ? warehouseDeviceInput.getQuantity() : 0);
		        row.createCell(4).setCellValue(warehouseDeviceInput.getComments() != null ? warehouseDeviceInput.getComments() : "");
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
		Specification<WarehouseDeviceOutput> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseDeviceOutputSpecification.getByWarehouse(request.getIdWarehouse()));
		}
		if(request.getInitialDate() != null && request.getFinalDate() != null)
		{
			specification = specification.and(WarehouseDeviceOutputSpecification.getByDate(request.getInitialDate(), request.getFinalDate()));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseDeviceOutputSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseDeviceOutput> warehouseDeviceOutputs = warehouseDeviceOutputRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Salidas");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseDeviceOutput warehouseDeviceOutput : warehouseDeviceOutputs) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseDeviceOutput.getWarehouseDevice().getWarehouse().getName());
		        row.createCell(1).setCellValue(warehouseDeviceOutput.getWarehouseDevice().getDevice().getManufacterPartNumber());
		        row.createCell(2).setCellValue(simpleDateFormat.format(warehouseDeviceOutput.getDate()));
		        row.createCell(3).setCellValue(warehouseDeviceOutput.getQuantity() != null ? warehouseDeviceOutput.getQuantity(): 0);
		        row.createCell(5).setCellValue(warehouseDeviceOutput.getComments() != null ? warehouseDeviceOutput.getComments() : "");
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
		Specification<WarehouseDeviceTransfer> specification = Specification.where(null);
		
		if(request.getIdWarehouse() != null) {
			specification = specification.and(WarehouseDeviceTransferSpecification.getByWarehouseOrigin(request.getIdWarehouse())
					.or(WarehouseDeviceTransferSpecification.getByWarehouseDestination(request.getIdWarehouse())));
		}
		if(request.getInitialDate() != null && request.getFinalDate() != null)
		{
			specification = specification.and(WarehouseDeviceTransferSpecification.getByDateOrigin(request.getInitialDate(), request.getFinalDate())
					.or(WarehouseDeviceTransferSpecification.getByDateDestination(request.getInitialDate(), request.getFinalDate())));
		}
		if(request.getManufacterPartNumber() != null && !"".equals(request.getManufacterPartNumber())){
			specification = specification.and(WarehouseDeviceTransferSpecification.getByManufacterPartNumber(request.getManufacterPartNumber()));
		}
		
		List<WarehouseDeviceTransfer> warehouseDeviceTransfers = warehouseDeviceTransferRepository.findAll(specification);
		
		try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
			Sheet sheet = workbook.createSheet("Transferencias");
			
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < headers.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(headers[col]);
		      }
			
			int rowIndex = 1;
			for (WarehouseDeviceTransfer warehouseDeviceTransfer : warehouseDeviceTransfers) {
				Row row = sheet.createRow(rowIndex++);
				
				row.createCell(0).setCellValue(warehouseDeviceTransfer.getOrigin().getWarehouse().getName());
				if(warehouseDeviceTransfer.getDestination() != null) {
					row.createCell(1).setCellValue(warehouseDeviceTransfer.getDestination().getWarehouse().getName());
				}
				
				switch (warehouseDeviceTransfer.getStatus()) {
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
				
		        row.createCell(3).setCellValue(warehouseDeviceTransfer.getOrigin().getDevice().getManufacterPartNumber());
		        
		        row.createCell(4).setCellValue(simpleDateFormat.format(warehouseDeviceTransfer.getOrigin().getDate()));
		        if(warehouseDeviceTransfer.getDestination() != null) {
					row.createCell(5).setCellValue(warehouseDeviceTransfer.getDestination().getDate() != null ? simpleDateFormat.format(warehouseDeviceTransfer.getDestination().getDate()) : "");
				}
		        
		        row.createCell(6).setCellValue(warehouseDeviceTransfer.getOrigin().getQuantity() != null ? warehouseDeviceTransfer.getOrigin().getQuantity() : 0);
		        if(warehouseDeviceTransfer.getDestination() != null) {
					row.createCell(7).setCellValue(warehouseDeviceTransfer.getDestination().getQuantity() != null ? warehouseDeviceTransfer.getDestination().getQuantity() : 0);
				}
		        
		        row.createCell(8).setCellValue(warehouseDeviceTransfer.getOrigin().getComments() != null ? warehouseDeviceTransfer.getOrigin().getComments() : "");
		        if(warehouseDeviceTransfer.getDestination() != null) {
		        	row.createCell(9).setCellValue(warehouseDeviceTransfer.getDestination().getComments() != null ? warehouseDeviceTransfer.getDestination().getComments() : "");
				}
			}
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception exception) {
			throw new ErrorException(0, "Error generating the report");
	    }
	}
}