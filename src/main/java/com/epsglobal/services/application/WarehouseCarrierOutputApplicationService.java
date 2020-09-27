package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.CarrierRepository;
import com.epsglobal.services.dataaccess.UserRepository;
import com.epsglobal.services.dataaccess.WarehouseCarrierOutputRepository;
import com.epsglobal.services.dataaccess.WarehouseCarrierRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.carrier.output.AddWarehouseCarrierOutputRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.output.AddWarehouseCarrierOutputResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.output.GetWarehouseCarrierOutputResponse;
import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseCarrier;
import com.epsglobal.services.domain.WarehouseCarrierOutput;

import lombok.NonNull;

@Service
public class WarehouseCarrierOutputApplicationService {	
	@Autowired
    private WarehouseRepository warehouseRepository;
	
	@Autowired
    private CarrierRepository carrierRepository;
	
	@Autowired
    private WarehouseCarrierRepository warehouseCarrierRepository;
	
	@Autowired
    private WarehouseCarrierOutputRepository warehouseCarrierOutputRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	public List<GetWarehouseCarrierOutputResponse> findAll(Long idWarehouse, Long idCarrier) {
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
		
		return warehouseCarrier
				.getWarehouseCarrierOutputs()
				.stream()
				.sorted(Comparator.comparing(WarehouseCarrierOutput::getId))
				.map(warehouseCarrierInput -> new GetWarehouseCarrierOutputResponse(warehouseCarrierInput))
				.collect(Collectors.toList());
	}
	
	public AddWarehouseCarrierOutputResponse add(Long idWarehouse, Long idCarrier, @NonNull AddWarehouseCarrierOutputRequest request) throws NotFoundException, InvalidException {
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
		
		Optional<User> optionalUser = userRepository.findById(request.getIdUser());
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();
		WarehouseCarrier warehouseCarrier = optionalWarehouseCarrier.get();
		
		long onHandComplete = warehouseCarrier.getOnHandComplete() == null ? 0 : warehouseCarrier.getOnHandComplete();
		long onHandPartial = warehouseCarrier.getOnHandPartial() == null ? 0 : warehouseCarrier.getOnHandPartial();
		long minimium = warehouseCarrier.getMinimium();
		onHandComplete -= request.getComplete() == null ? 0 : request.getComplete();
		onHandPartial -= request.getPartial() == null ? 0 : request.getPartial();
		
		if(onHandComplete < minimium) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_CARRIER_MINIMIUM,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_MINIMIUM));
		}
		
		WarehouseCarrierOutput warehouseCarrierOutput = new WarehouseCarrierOutput();
		warehouseCarrierOutput.setDate(new Date());
		warehouseCarrierOutput.setOrder(request.getOrder());
		warehouseCarrierOutput.setComments(request.getComments());
		warehouseCarrierOutput.setComplete(request.getComplete());
		warehouseCarrierOutput.setPartial(request.getPartial());
		warehouseCarrierOutput.setPriceByReel(request.getPriceByReel());
		warehouseCarrierOutput.setCostByReel(request.getCostByReel());
		warehouseCarrierOutput.setCostByMeter(request.getCostByMeter());
		warehouseCarrierOutput.setProfit(request.getProfit());
		warehouseCarrierOutput.setUser(user);
		
		warehouseCarrier.setOnHandComplete(onHandComplete);
		warehouseCarrier.setOnHandPartial(onHandPartial);
		warehouseCarrier.getWarehouseCarrierOutputs().add(warehouseCarrierOutput);
		
		warehouseCarrierOutputRepository.save(warehouseCarrierOutput);
		warehouseCarrierRepository.save(warehouseCarrier);
		
		AddWarehouseCarrierOutputResponse response = new AddWarehouseCarrierOutputResponse(warehouseCarrierOutput);
		
		return response;
	}
}
