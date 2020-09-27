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
import com.epsglobal.services.dataaccess.WarehouseCarrierInputRepository;
import com.epsglobal.services.dataaccess.WarehouseCarrierRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.carrier.input.AddWarehouseCarrierInputRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.input.AddWarehouseCarrierInputResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.input.GetWarehouseCarrierInputResponse;
import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseCarrier;
import com.epsglobal.services.domain.WarehouseCarrierInput;

import lombok.NonNull;

@Service
public class WarehouseCarrierInputApplicationService {	
	@Autowired
    private WarehouseRepository warehouseRepository;
	
	@Autowired
    private CarrierRepository carrierRepository;
	
	@Autowired
    private WarehouseCarrierRepository warehouseCarrierRepository;
	
	@Autowired
    private WarehouseCarrierInputRepository warehouseCarrierInputRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	public List<GetWarehouseCarrierInputResponse> findAll(Long idWarehouse, Long idCarrier) {
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
				.getWarehouseCarrierInputs()
				.stream()
				.sorted(Comparator.comparing(WarehouseCarrierInput::getId))
				.map(warehouseCarrierInput -> new GetWarehouseCarrierInputResponse(warehouseCarrierInput))
				.collect(Collectors.toList());
	}
	
	public AddWarehouseCarrierInputResponse add(Long idWarehouse, Long idCarrier, @NonNull AddWarehouseCarrierInputRequest request) throws NotFoundException, InvalidException {
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
		long maximium = warehouseCarrier.getMaximium();
		onHandComplete += request.getComplete() == null ? 0 : request.getComplete();
		onHandPartial += request.getPartial() == null ? 0 : request.getPartial();
		
		if(onHandComplete > maximium) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_CARRIER_MAXIMIUM,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_MAXIMIUM));
		}
		
		WarehouseCarrierInput warehouseCarrierInput = new WarehouseCarrierInput();
		warehouseCarrierInput.setDate(new Date());
		warehouseCarrierInput.setOrder(request.getOrder());
		warehouseCarrierInput.setComments(request.getComments());
		warehouseCarrierInput.setComplete(request.getComplete());
		warehouseCarrierInput.setPartial(request.getPartial());
		warehouseCarrierInput.setPriceByReel(request.getPriceByReel());
		warehouseCarrierInput.setCostByReel(request.getCostByReel());
		warehouseCarrierInput.setCostByMeter(request.getCostByMeter());
		warehouseCarrierInput.setProfit(request.getProfit());
		warehouseCarrierInput.setUser(user);
		
		warehouseCarrier.setOnHandComplete(onHandComplete);
		warehouseCarrier.setOnHandPartial(onHandPartial);
		warehouseCarrier.getWarehouseCarrierInputs().add(warehouseCarrierInput);
		
		warehouseCarrierInputRepository.save(warehouseCarrierInput);
		warehouseCarrierRepository.save(warehouseCarrier);
		
		AddWarehouseCarrierInputResponse response = new AddWarehouseCarrierInputResponse(warehouseCarrierInput);
		
		return response;
	}
}
