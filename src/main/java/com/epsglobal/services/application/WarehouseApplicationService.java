package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.LocationRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.AddWarehouseRequest;
import com.epsglobal.services.datatransfer.warehouse.AddWarehouseResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.datatransfer.warehouse.UpdateWarehouseRequest;
import com.epsglobal.services.datatransfer.warehouse.UpdateWarehouseResponse;
import com.epsglobal.services.domain.Location;
import com.epsglobal.services.domain.Warehouse;

import lombok.NonNull;

@Service
public class WarehouseApplicationService {
	@Autowired
    private LocationRepository locationRepository;
	
	@Autowired
    private WarehouseRepository warehouseRepository;
	
	public List<GetWarehouseResponse> findAll() {
		List<Warehouse> warehouses = warehouseRepository.findAll();

		return warehouses
				.stream()
				.sorted(Comparator.comparing(Warehouse::getId))
				.map(warehouse -> new GetWarehouseResponse(warehouse))
				.collect(Collectors.toList());
	}
	
	public GetWarehouseResponse findById(Long id) throws NotFoundException {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		return new GetWarehouseResponse(optionalWarehouse.get());
	}
	
	public AddWarehouseResponse add(@NonNull AddWarehouseRequest request) throws InvalidException {
		Optional<Warehouse> optionalWarehouseByName = warehouseRepository.findByName(request.getName());
		
		if (optionalWarehouseByName.isPresent()) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_EXISTS));
		}
		
		Warehouse warehouse = new Warehouse();
		
		warehouse.setName(request.getName());
		
		Optional<Location> optionalLocation = locationRepository.findById(request.getIdLocation());
		warehouse.setLocation(optionalLocation.get());
		
		warehouseRepository.save(warehouse);
		
		AddWarehouseResponse response = new AddWarehouseResponse(warehouse);

		return response;
	}
	
	public UpdateWarehouseResponse update(long id, @NonNull UpdateWarehouseRequest request) throws NotFoundException, InvalidException {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Optional<Warehouse> optionalWarehouseByName = warehouseRepository.findByName(request.getName());
		
		if (optionalWarehouseByName.isPresent()) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_EXISTS));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		warehouse.setName(request.getName());
		
		Optional<Location> optionalLocation = locationRepository.findById(request.getIdLocation());
		warehouse.setLocation(optionalLocation.get());
		
		warehouseRepository.save(warehouse);
		
		UpdateWarehouseResponse response = new UpdateWarehouseResponse(warehouse);

		return response;
	}
}
