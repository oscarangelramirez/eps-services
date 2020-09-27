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
import com.epsglobal.services.dataaccess.WarehouseDirectOutputRepository;
import com.epsglobal.services.dataaccess.WarehouseDirectRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.direct.output.AddWarehouseDirectOutputRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.output.AddWarehouseDirectOutputResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.output.GetWarehouseDirectOutputResponse;
import com.epsglobal.services.domain.Direct;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseDirect;
import com.epsglobal.services.domain.WarehouseDirectOutput;

import lombok.NonNull;

@Service
public class WarehouseDirectOutputApplicationService {	
	@Autowired
    private WarehouseRepository warehouseRepository;
	
	@Autowired
    private DirectRepository directRepository;
	
	@Autowired
    private WarehouseDirectRepository warehouseDirectRepository;
	
	@Autowired
    private WarehouseDirectOutputRepository warehouseDirectOutputRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	public List<GetWarehouseDirectOutputResponse> findAll(Long idWarehouse, Long idDirect) {
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
				.getWarehouseDirectOutputs()
				.stream()
				.sorted(Comparator.comparing(WarehouseDirectOutput::getId))
				.map(warehouseDirectInput -> new GetWarehouseDirectOutputResponse(warehouseDirectInput))
				.collect(Collectors.toList());
	}
	
	public AddWarehouseDirectOutputResponse add(Long idWarehouse, Long idDirect, @NonNull AddWarehouseDirectOutputRequest request) throws NotFoundException, InvalidException {
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
		long minimium = warehouseDirect.getMinimium();
		onHand -= request.getQuantity() == null ? 0 : request.getQuantity();
		
		if(onHand < minimium) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_DIRECT_MINIMIUM,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_MINIMIUM));
		}
		
		WarehouseDirectOutput warehouseDirectOutput = new WarehouseDirectOutput();
		warehouseDirectOutput.setDate(new Date());
		warehouseDirectOutput.setOrder(request.getOrder());
		warehouseDirectOutput.setComments(request.getComments());
		warehouseDirectOutput.setQuantity(request.getQuantity());
		warehouseDirectOutput.setPrice(request.getPrice());
		warehouseDirectOutput.setCost(request.getCost());
		warehouseDirectOutput.setProfit(request.getProfit());
		warehouseDirectOutput.setUser(user);
		
		warehouseDirect.setOnHand(onHand);
		warehouseDirect.getWarehouseDirectOutputs().add(warehouseDirectOutput);
		
		warehouseDirectOutputRepository.save(warehouseDirectOutput);
		warehouseDirectRepository.save(warehouseDirect);
		
		AddWarehouseDirectOutputResponse response = new AddWarehouseDirectOutputResponse(warehouseDirectOutput);
		
		return response;
	}
}
