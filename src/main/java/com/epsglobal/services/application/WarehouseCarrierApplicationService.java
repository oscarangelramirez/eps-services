package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.CarrierRepository;
import com.epsglobal.services.dataaccess.WarehouseCarrierRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.carrier.GetCarrierResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.AddWarehouseCarrierRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.AddWarehouseCarrierResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.AddWarehouseCarriersRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.AddWarehouseCarriersResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.GetWarehouseCarrierResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.UpdateWarehouseCarrierRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.UpdateWarehouseCarrierResponse;
import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseCarrier;

import lombok.NonNull;

@Service
public class WarehouseCarrierApplicationService {	
	@Autowired
    private WarehouseRepository warehouseRepository;
	
	@Autowired
    private CarrierRepository carrierRepository;
	
	@Autowired
    private WarehouseCarrierRepository warehouseCarrierRepository;
	
	public List<GetWarehouseCarrierResponse> findAll(Long idWarehouse) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		List<WarehouseCarrier> warehouseCarriers = warehouseCarrierRepository.findByWarehouse(warehouse);

		return warehouseCarriers
				.stream()
				.sorted(Comparator.comparing(WarehouseCarrier::getId))
				.map(warehouseCarrier -> new GetWarehouseCarrierResponse(warehouseCarrier))
				.collect(Collectors.toList());
	}
	
	public GetWarehouseCarrierResponse findById(Long idWarehouse, Long idCarrier) throws NotFoundException {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		Optional<Carrier> optionalCarrier = carrierRepository.findById(idCarrier);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		if (!optionalCarrier.isPresent()) {
			throw new NotFoundException(CodeExceptions.CARRIER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.CARRIER_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		Carrier carrier = optionalCarrier.get();
		
		Optional<WarehouseCarrier> optionalWarehouseCarrier = warehouseCarrierRepository.findByWarehouseAndCarrier(warehouse, carrier);
		
		if (!optionalWarehouseCarrier.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_CARRIER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_NOT_FOUND));
		}
		
		return new GetWarehouseCarrierResponse(optionalWarehouseCarrier.get());
	}
	
	public AddWarehouseCarriersResponse add(Long idWarehouse, @NonNull AddWarehouseCarriersRequest request) throws NotFoundException {
		AddWarehouseCarriersResponse response = new AddWarehouseCarriersResponse();
		
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		
		for (AddWarehouseCarrierRequest warehouseCarrierRquest : request.getWarehouseCarriers()) {
			Optional<Carrier> optionalCarrier = carrierRepository.findById(warehouseCarrierRquest.getIdCarrier());
			
			if(optionalCarrier.isPresent()) {
				Carrier carrier = optionalCarrier.get();
				
				WarehouseCarrier warehouseCarrier = new WarehouseCarrier();
				warehouseCarrier.setWarehouse(warehouse);
				warehouseCarrier.setCarrier(carrier);
				warehouseCarrier.setMinimium(request.getMinimium());
				warehouseCarrier.setMaximium(request.getMaximium());
				
				warehouseCarrierRepository.save(warehouseCarrier);
				
				AddWarehouseCarrierResponse warehouseCarrierResponse = new AddWarehouseCarrierResponse(warehouseCarrier);
				
				response.getWarehouseCarriers().add(warehouseCarrierResponse);
			}
		}
		
		return response;
	}
	
	public UpdateWarehouseCarrierResponse update(Long idWarehouse, Long idCarrier, @NonNull UpdateWarehouseCarrierRequest request) throws NotFoundException {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		Optional<Carrier> optionalCarrier = carrierRepository.findById(idCarrier);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		if (!optionalCarrier.isPresent()) {
			throw new NotFoundException(CodeExceptions.CARRIER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.CARRIER_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		Carrier carrier = optionalCarrier.get();
		
		Optional<WarehouseCarrier> optionalWarehouseCarrier = warehouseCarrierRepository.findByWarehouseAndCarrier(warehouse, carrier);
		
		if (!optionalWarehouseCarrier.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_CARRIER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_NOT_FOUND));
		}
		
		WarehouseCarrier warehouseCarrier = optionalWarehouseCarrier.get();
		warehouseCarrier.setMinimium(request.getMinimium());
		warehouseCarrier.setMaximium(request.getMaximium());
		
		warehouseCarrierRepository.save(warehouseCarrier);
		
		UpdateWarehouseCarrierResponse response = new UpdateWarehouseCarrierResponse(warehouseCarrier);

		return response;
	}
	
	public List<GetCarrierResponse> findAllNotInWarehouse(Long idWarehouse) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		List<Carrier> carriers = carrierRepository.findAll();
		
		Warehouse warehouse = optionalWarehouse.get();
		List<WarehouseCarrier> warehouseCarriers = warehouseCarrierRepository.findByWarehouse(warehouse);
		
		var carriersNotInWareHouse = carriers.stream()
		.filter(c -> !warehouseCarriers.stream().anyMatch(w -> w.getCarrier().getId().equals(c.getId())));

		return carriersNotInWareHouse
				.sorted(Comparator.comparing(Carrier::getId))
				.map(carrier -> new GetCarrierResponse(carrier))
				.collect(Collectors.toList());
	}
}
