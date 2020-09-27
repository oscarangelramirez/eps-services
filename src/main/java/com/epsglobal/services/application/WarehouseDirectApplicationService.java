package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.DirectRepository;
import com.epsglobal.services.dataaccess.WarehouseDirectRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.direct.GetDirectResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.AddWarehouseDirectRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.AddWarehouseDirectResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.AddWarehouseDirectsRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.AddWarehouseDirectsResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.GetWarehouseDirectResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.UpdateWarehouseDirectRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.UpdateWarehouseDirectResponse;
import com.epsglobal.services.domain.Direct;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseDirect;

import lombok.NonNull;

@Service
public class WarehouseDirectApplicationService {	
	@Autowired
    private WarehouseRepository warehouseRepository;
	
	@Autowired
    private DirectRepository directRepository;
	
	@Autowired
    private WarehouseDirectRepository warehouseDirectRepository;
	
	public List<GetWarehouseDirectResponse> findAll(Long idWarehouse) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		List<WarehouseDirect> warehouseDirects = warehouseDirectRepository.findByWarehouse(warehouse);

		return warehouseDirects
				.stream()
				.sorted(Comparator.comparing(WarehouseDirect::getId))
				.map(warehouseDirect -> new GetWarehouseDirectResponse(warehouseDirect))
				.collect(Collectors.toList());
	}
	
	public GetWarehouseDirectResponse findById(Long idWarehouse, Long idDirect) throws NotFoundException {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		Optional<Direct> optionalDirect = directRepository.findById(idDirect);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		if (!optionalDirect.isPresent()) {
			throw new NotFoundException(CodeExceptions.DIRECT_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DIRECT_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		Direct direct = optionalDirect.get();
		
		Optional<WarehouseDirect> optionalWarehouseDirect = warehouseDirectRepository.findByWarehouseAndDirect(warehouse, direct);
		
		if (!optionalWarehouseDirect.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DIRECT_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_NOT_FOUND));
		}
		
		return new GetWarehouseDirectResponse(optionalWarehouseDirect.get());
	}
	
	public AddWarehouseDirectsResponse add(Long idWarehouse, @NonNull AddWarehouseDirectsRequest request) throws NotFoundException {
		AddWarehouseDirectsResponse response = new AddWarehouseDirectsResponse();
		
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		
		for (AddWarehouseDirectRequest warehouseDirectRquest : request.getWarehouseDirects()) {
			Optional<Direct> optionalDirect = directRepository.findById(warehouseDirectRquest.getIdDirect());
			
			if(optionalDirect.isPresent()) {
				Direct Direct = optionalDirect.get();
				
				WarehouseDirect warehouseDirect = new WarehouseDirect();
				warehouseDirect.setWarehouse(warehouse);
				warehouseDirect.setDirect(Direct);
				warehouseDirect.setMinimium(request.getMinimium());
				warehouseDirect.setMaximium(request.getMaximium());
				
				warehouseDirectRepository.save(warehouseDirect);
				
				AddWarehouseDirectResponse warehouseDirectResponse = new AddWarehouseDirectResponse(warehouseDirect);
				
				response.getWarehouseDirects().add(warehouseDirectResponse);
			}
		}
		
		return response;
	}
	
	public UpdateWarehouseDirectResponse update(Long idWarehouse, Long idDirect, @NonNull UpdateWarehouseDirectRequest request) throws NotFoundException {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		Optional<Direct> optionalDirect = directRepository.findById(idDirect);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		if (!optionalDirect.isPresent()) {
			throw new NotFoundException(CodeExceptions.DIRECT_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DIRECT_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		Direct direct = optionalDirect.get();
		
		Optional<WarehouseDirect> optionalWarehouseDirect = warehouseDirectRepository.findByWarehouseAndDirect(warehouse, direct);
		
		if (!optionalWarehouseDirect.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DIRECT_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_NOT_FOUND));
		}
		
		WarehouseDirect warehouseDirect = optionalWarehouseDirect.get();
		warehouseDirect.setMinimium(request.getMinimium());
		warehouseDirect.setMaximium(request.getMaximium());
		
		warehouseDirectRepository.save(warehouseDirect);
		
		UpdateWarehouseDirectResponse response = new UpdateWarehouseDirectResponse(warehouseDirect);

		return response;
	}
	
	public List<GetDirectResponse> findAllNotInWarehouse(Long idWarehouse) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		List<Direct> directs = directRepository.findAll();
		
		Warehouse warehouse = optionalWarehouse.get();
		List<WarehouseDirect> warehouseDirects = warehouseDirectRepository.findByWarehouse(warehouse);
		
		var directsNotInWareHouse = directs.stream()
		.filter(c -> !warehouseDirects.stream().anyMatch(w -> w.getDirect().getId().equals(c.getId())));

		return directsNotInWareHouse
				.sorted(Comparator.comparing(Direct::getId))
				.map(Direct -> new GetDirectResponse(Direct))
				.collect(Collectors.toList());
	}
}
