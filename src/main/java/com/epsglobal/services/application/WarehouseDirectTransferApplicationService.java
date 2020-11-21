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
import com.epsglobal.services.dataaccess.WarehouseDirectRepository;
import com.epsglobal.services.dataaccess.WarehouseDirectTransferPlaceRepository;
import com.epsglobal.services.dataaccess.WarehouseDirectTransferRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.SendWarehouseDirectTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.SendWarehouseDirectTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.GetWarehouseDirectTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.ReceiveWarehouseDirectTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.direct.transfer.ReceiveWarehouseDirectTransferResponse;
import com.epsglobal.services.domain.Direct;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseDirect;
import com.epsglobal.services.domain.WarehouseDirectTransfer;
import com.epsglobal.services.domain.WarehouseDirectTransferPlace;

import lombok.NonNull;

@Service
public class WarehouseDirectTransferApplicationService {	
	@Autowired
    private WarehouseRepository warehouseRepository;
	
	@Autowired
    private DirectRepository directRepository;
	
	@Autowired
    private WarehouseDirectRepository warehouseDirectRepository;
	
	@Autowired
    private WarehouseDirectTransferRepository warehouseDirectTransferRepository;
	
	@Autowired
    private WarehouseDirectTransferPlaceRepository warehouseDirectTransferPlaceRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	public List<GetWarehouseDirectTransferResponse> findAll(Long idWarehouse, Long idDirect) {
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
				.getWarehouseDirectTransfers()
				.stream()
				.sorted(Comparator.comparing(WarehouseDirectTransfer::getId))
				.map(warehouseDirectTransfer -> new GetWarehouseDirectTransferResponse(warehouseDirectTransfer))
				.collect(Collectors.toList());
	}
	
	public GetWarehouseDirectTransferResponse findById(Long id) {		
		Optional<WarehouseDirectTransfer> optionalWarehouseDirectTransfer = warehouseDirectTransferRepository.findById(id);
		
		if (!optionalWarehouseDirectTransfer.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DIRECT_TRANSFER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_TRANSFER_NOT_FOUND));
		}
		
		return new GetWarehouseDirectTransferResponse(optionalWarehouseDirectTransfer.get());
	}
	
	public SendWarehouseDirectTransferResponse send(Long idWarehouse, Long idDirect, @NonNull SendWarehouseDirectTransferRequest request) throws NotFoundException, InvalidException {
		Optional<Direct> optionalDirect = directRepository.findById(idDirect);
		
		if (!optionalDirect.isPresent()) {
			throw new NotFoundException(CodeExceptions.DIRECT_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DIRECT_NOT_FOUND));
		}
		
		Direct direct = optionalDirect.get();
		
		Optional<Warehouse> optionalWarehouseOrigin = warehouseRepository.findById(request.getOrigin().getIdWarehouse());
		
		if (!optionalWarehouseOrigin.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Optional<Warehouse> optionalWarehouseDestination = warehouseRepository.findById(request.getDestination().getIdWarehouse());
		
		if (!optionalWarehouseDestination.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Warehouse warehouseOrigin = optionalWarehouseOrigin.get();
		Optional<WarehouseDirect> optionalWarehouseDirectOrigin = warehouseDirectRepository.findByWarehouseAndDirect(warehouseOrigin, direct);
		
		if (!optionalWarehouseDirectOrigin.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DIRECT_ORIGIN_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_ORIGIN_NOT_FOUND));
		}
		
		WarehouseDirect warehouseDirectOrigin = optionalWarehouseDirectOrigin.get();
		
		Warehouse warehouseDestination = optionalWarehouseDestination.get();
		Optional<WarehouseDirect> optionalWarehouseDirectDestination = warehouseDirectRepository.findByWarehouseAndDirect(warehouseDestination, direct);
		
		if (!optionalWarehouseDirectDestination.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DIRECT_DESTINATION_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_DESTINATION_NOT_FOUND));
		}
		
		WarehouseDirect warehouseDirectDestination = optionalWarehouseDirectDestination.get();
		
		Optional<User> optionalUser = userRepository.findById(request.getOrigin().getIdUser());
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();
		
		WarehouseDirectTransferPlace origin = new WarehouseDirectTransferPlace();
		origin.setDate(new Date());
		origin.setComments(request.getOrigin().getComments());
		origin.setQuantity(request.getOrigin().getQuantity());
		origin.setPrice(request.getOrigin().getPrice());
		origin.setCost(request.getOrigin().getCost());
		origin.setProfit(request.getOrigin().getProfit());
		origin.setWarehouse(warehouseOrigin);
		origin.setDirect(direct);
		origin.setUser(user);
		
		WarehouseDirectTransferPlace destination = new WarehouseDirectTransferPlace();
		destination.setDate(null);
		destination.setComments(null);
		destination.setQuantity(null);
		destination.setPrice(request.getDestination().getPrice());
		destination.setCost(request.getDestination().getCost());
		destination.setProfit(request.getDestination().getProfit());
		destination.setWarehouse(warehouseDestination);
		destination.setDirect(direct);
		destination.setUser(null);
		
		WarehouseDirectTransfer warehouseDirectTransfer = new WarehouseDirectTransfer();
		warehouseDirectTransfer.setStatus(WarehouseDirectTransfer.Status.SENT);
		warehouseDirectTransfer.setOrigin(origin);
		warehouseDirectTransfer.setDestination(destination);
		
		long onHand = warehouseDirectOrigin.getOnHand() == null ? 0 : warehouseDirectOrigin.getOnHand();
		long minimium = warehouseDirectOrigin.getMinimium();
		onHand -= request.getOrigin().getQuantity() == null ? 0 : request.getOrigin().getQuantity();
		
		if(onHand < minimium) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_DIRECT_MINIMIUM,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_MINIMIUM));
		}
		
		warehouseDirectOrigin.setOnHand(onHand);
		
		warehouseDirectTransferPlaceRepository.save(origin);
		warehouseDirectTransferPlaceRepository.save(destination);
		warehouseDirectTransferRepository.save(warehouseDirectTransfer);
		
		warehouseDirectOrigin.getWarehouseDirectTransfers().add(warehouseDirectTransfer);
		warehouseDirectDestination.getWarehouseDirectTransfers().add(warehouseDirectTransfer);

		warehouseDirectRepository.save(warehouseDirectOrigin);
		warehouseDirectRepository.save(warehouseDirectDestination);
		
		SendWarehouseDirectTransferResponse response = new SendWarehouseDirectTransferResponse(warehouseDirectTransfer);
		
		return response;
	}
	
	public ReceiveWarehouseDirectTransferResponse receive(Long idWarehouse, Long idDirect, Long id, @NonNull ReceiveWarehouseDirectTransferRequest request) throws NotFoundException, InvalidException {
		Optional<Direct> optionalDirect = directRepository.findById(idDirect);
		
		if (!optionalDirect.isPresent()) {
			throw new NotFoundException(CodeExceptions.DIRECT_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DIRECT_NOT_FOUND));
		}
		
		Direct direct = optionalDirect.get();
		
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		Optional<WarehouseDirect> optionalWarehouseDirect = warehouseDirectRepository.findByWarehouseAndDirect(warehouse, direct);
		
		if (!optionalWarehouseDirect.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DIRECT_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_NOT_FOUND));
		}
		
		WarehouseDirect warehouseDirect = optionalWarehouseDirect.get();
		
		Optional<User> optionalUser = userRepository.findById(request.getDestination().getIdUser());
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();
		
		Optional<WarehouseDirectTransfer> optionalWarehouseDirectTransfer = warehouseDirectTransferRepository.findById(id);
		
		if (!optionalWarehouseDirectTransfer.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DIRECT_TRANSFER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_TRANSFER_NOT_FOUND));
		}
		
		WarehouseDirectTransfer warehouseDirectTransfer = optionalWarehouseDirectTransfer.get();
		
		WarehouseDirectTransferPlace destination = warehouseDirectTransfer.getDestination();
		destination.setDate(new Date());
		destination.setComments(request.getDestination().getComments());
		destination.setQuantity(request.getDestination().getQuantity());
		destination.setPrice(request.getDestination().getPrice());
		destination.setCost(request.getDestination().getCost());
		destination.setProfit(request.getDestination().getProfit());
		destination.setWarehouse(warehouse);
		destination.setUser(user);
		
		warehouseDirectTransfer.setStatus(WarehouseDirectTransfer.Status.RECEIVED);
		
		long onHand = warehouseDirect.getOnHand() == null ? 0 : warehouseDirect.getOnHand();
		long maximium = warehouseDirect.getMaximium();
		onHand += request.getDestination().getQuantity() == null ? 0 : request.getDestination().getQuantity();
		
		if(onHand > maximium) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_DIRECT_MAXIMIUM,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DIRECT_MAXIMIUM));
		}
		
		warehouseDirect.setOnHand(onHand);
		
		warehouseDirectTransferPlaceRepository.save(destination);
		warehouseDirectTransferRepository.save(warehouseDirectTransfer);
		warehouseDirectRepository.save(warehouseDirect);
		
		ReceiveWarehouseDirectTransferResponse response = new ReceiveWarehouseDirectTransferResponse(warehouseDirectTransfer);
		
		return response;
	}
}
