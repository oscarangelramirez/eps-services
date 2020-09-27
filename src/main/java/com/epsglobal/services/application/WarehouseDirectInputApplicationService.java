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
import com.epsglobal.services.dataaccess.DirectRepository;
import com.epsglobal.services.dataaccess.UserRepository;
import com.epsglobal.services.dataaccess.WarehouseDirectInputRepository;
import com.epsglobal.services.dataaccess.WarehouseDirectRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.direct.input.AddWarehouseDirectInputRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.input.AddWarehouseDirectInputResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.input.GetWarehouseDirectInputResponse;
import com.epsglobal.services.domain.Direct;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseDirect;
import com.epsglobal.services.domain.WarehouseDirectInput;

import lombok.NonNull;

@Service
public class WarehouseDirectInputApplicationService {	
	@Autowired
    private WarehouseRepository warehouseRepository;
	
	@Autowired
    private DirectRepository directRepository;
	
	@Autowired
    private WarehouseDirectRepository warehouseDirectRepository;
	
	@Autowired
    private WarehouseDirectInputRepository warehouseDirectInputRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	public List<GetWarehouseDirectInputResponse> findAll(Long idWarehouse, Long idDirect) {
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
		
		return warehouseDirect
				.getWarehouseDirectInputs()
				.stream()
				.sorted(Comparator.comparing(WarehouseDirectInput::getId))
				.map(warehouseDirectInput -> new GetWarehouseDirectInputResponse(warehouseDirectInput))
				.collect(Collectors.toList());
	}
	
	public AddWarehouseDirectInputResponse add(Long idWarehouse, Long idDirect, @NonNull AddWarehouseDirectInputRequest request) throws NotFoundException, InvalidException {
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
		
		Optional<User> optionalUser = userRepository.findById(request.getIdUser());
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();
		WarehouseDirect warehouseDirect = optionalWarehouseDirect.get();
		
		long onHand = warehouseDirect.getOnHand() == null ? 0 : warehouseDirect.getOnHand();
		long maximium = warehouseDirect.getMaximium();
		onHand += request.getQuantity() == null ? 0 : request.getQuantity();
		
		if(onHand > maximium) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_DIRECT_MAXIMIUM,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_MAXIMIUM));
		}
		
		WarehouseDirectInput warehouseDirectInput = new WarehouseDirectInput();
		warehouseDirectInput.setDate(new Date());
		warehouseDirectInput.setOrder(request.getOrder());
		warehouseDirectInput.setComments(request.getComments());
		warehouseDirectInput.setQuantity(request.getQuantity());
		warehouseDirectInput.setPrice(request.getPrice());
		warehouseDirectInput.setCost(request.getCost());
		warehouseDirectInput.setProfit(request.getProfit());
		warehouseDirectInput.setUser(user);
		
		warehouseDirect.setOnHand(onHand);
		warehouseDirect.getWarehouseDirectInputs().add(warehouseDirectInput);
		
		warehouseDirectInputRepository.save(warehouseDirectInput);
		warehouseDirectRepository.save(warehouseDirect);
		
		AddWarehouseDirectInputResponse response = new AddWarehouseDirectInputResponse(warehouseDirectInput);
		
		return response;
	}
}
