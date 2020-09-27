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
import com.epsglobal.services.dataaccess.WarehouseCarrierRepository;
import com.epsglobal.services.dataaccess.WarehouseCarrierTransferPlaceRepository;
import com.epsglobal.services.dataaccess.WarehouseCarrierTransferRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.SendWarehouseCarrierTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.SendWarehouseCarrierTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.GetWarehouseCarrierTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.ReceiveWarehouseCarrierTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.carrier.transfer.ReceiveWarehouseCarrierTransferResponse;
import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseCarrier;
import com.epsglobal.services.domain.WarehouseCarrierTransfer;
import com.epsglobal.services.domain.WarehouseCarrierTransferPlace;

import lombok.NonNull;

@Service
public class WarehouseCarrierTransferApplicationService {	
	@Autowired
    private WarehouseRepository warehouseRepository;
	
	@Autowired
    private CarrierRepository carrierRepository;
	
	@Autowired
    private WarehouseCarrierRepository warehouseCarrierRepository;
	
	@Autowired
    private WarehouseCarrierTransferRepository warehouseCarrierTransferRepository;
	
	@Autowired
    private WarehouseCarrierTransferPlaceRepository warehouseCarrierTransferPlaceRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	public List<GetWarehouseCarrierTransferResponse> findAll(Long idWarehouse, Long idCarrier) {
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
				.getWarehouseCarrierTransfers()
				.stream()
				.sorted(Comparator.comparing(WarehouseCarrierTransfer::getId))
				.map(warehouseCarrierTransfer -> new GetWarehouseCarrierTransferResponse(warehouseCarrierTransfer))
				.collect(Collectors.toList());
	}
	
	public GetWarehouseCarrierTransferResponse findById(Long id) {		
		Optional<WarehouseCarrierTransfer> optionalWarehouseCarrierTransfer = warehouseCarrierTransferRepository.findById(id);
		
		if (!optionalWarehouseCarrierTransfer.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_CARRIER_TRANSFER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_TRANSFER_NOT_FOUND));
		}
		
		return new GetWarehouseCarrierTransferResponse(optionalWarehouseCarrierTransfer.get());
	}
	
	public SendWarehouseCarrierTransferResponse send(Long idWarehouse, Long idCarrier, @NonNull SendWarehouseCarrierTransferRequest request) throws NotFoundException, InvalidException {
		Optional<Carrier> optionalCarrier = carrierRepository.findById(idCarrier);
		
		if (!optionalCarrier.isPresent()) {
			throw new NotFoundException(CodeExceptions.CARRIER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.CARRIER_NOT_FOUND));
		}
		
		Carrier carrier = optionalCarrier.get();
		
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
		Optional<WarehouseCarrier> optionalWarehouseCarrierOrigin = warehouseCarrierRepository.findByWarehouseAndCarrier(warehouseOrigin, carrier);
		
		if (!optionalWarehouseCarrierOrigin.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_CARRIER_ORIGIN_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_ORIGIN_NOT_FOUND));
		}
		
		WarehouseCarrier warehouseCarrierOrigin = optionalWarehouseCarrierOrigin.get();
		
		Warehouse warehouseDestination = optionalWarehouseDestination.get();
		Optional<WarehouseCarrier> optionalWarehouseCarrierDestination = warehouseCarrierRepository.findByWarehouseAndCarrier(warehouseDestination, carrier);
		
		if (!optionalWarehouseCarrierDestination.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_CARRIER_DESTINATION_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_DESTINATION_NOT_FOUND));
		}
		
		WarehouseCarrier warehouseCarrierDestination = optionalWarehouseCarrierDestination.get();
		
		Optional<User> optionalUser = userRepository.findById(request.getOrigin().getIdUser());
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();
		
		WarehouseCarrierTransferPlace origin = new WarehouseCarrierTransferPlace();
		origin.setDate(new Date());
		origin.setComments(request.getOrigin().getComments());
		origin.setComplete(request.getOrigin().getComplete());
		origin.setPartial(request.getOrigin().getPartial());
		origin.setPriceByReel(request.getOrigin().getPriceByReel());
		origin.setCostByReel(request.getOrigin().getCostByReel());
		origin.setCostByMeter(request.getOrigin().getCostByMeter());
		origin.setProfit(request.getOrigin().getProfit());
		origin.setWarehouse(warehouseOrigin);
		origin.setUser(user);
		
		WarehouseCarrierTransferPlace destination = new WarehouseCarrierTransferPlace();
		destination.setDate(null);
		destination.setComments(null);
		destination.setComplete(null);
		destination.setPartial(null);
		destination.setPriceByReel(request.getDestination().getPriceByReel());
		destination.setCostByReel(request.getDestination().getCostByReel());
		destination.setCostByMeter(request.getDestination().getCostByMeter());
		destination.setProfit(request.getDestination().getProfit());
		destination.setWarehouse(warehouseDestination);
		destination.setUser(null);
		
		WarehouseCarrierTransfer warehouseCarrierTransfer = new WarehouseCarrierTransfer();
		warehouseCarrierTransfer.setStatus(WarehouseCarrierTransfer.Status.SENT);
		warehouseCarrierTransfer.setOrigin(origin);
		warehouseCarrierTransfer.setDestination(destination);
		
		long onHandComplete = warehouseCarrierOrigin.getOnHandComplete() == null ? 0 : warehouseCarrierOrigin.getOnHandComplete();
		long onHandPartial = warehouseCarrierOrigin.getOnHandPartial() == null ? 0 : warehouseCarrierOrigin.getOnHandPartial();
		long minimium = warehouseCarrierOrigin.getMinimium();
		onHandComplete -= request.getOrigin().getComplete() == null ? 0 : request.getOrigin().getComplete();
		onHandPartial -= request.getOrigin().getPartial() == null ? 0 : request.getOrigin().getPartial();
		
		if(onHandComplete < minimium) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_CARRIER_MINIMIUM,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_MINIMIUM));
		}
		
		warehouseCarrierOrigin.setOnHandComplete(onHandComplete);
		warehouseCarrierOrigin.setOnHandPartial(onHandPartial);
		
		warehouseCarrierTransferPlaceRepository.save(origin);
		warehouseCarrierTransferPlaceRepository.save(destination);
		warehouseCarrierTransferRepository.save(warehouseCarrierTransfer);
		
		warehouseCarrierOrigin.getWarehouseCarrierTransfers().add(warehouseCarrierTransfer);
		warehouseCarrierDestination.getWarehouseCarrierTransfers().add(warehouseCarrierTransfer);

		warehouseCarrierRepository.save(warehouseCarrierOrigin);
		warehouseCarrierRepository.save(warehouseCarrierDestination);
		
		SendWarehouseCarrierTransferResponse response = new SendWarehouseCarrierTransferResponse(warehouseCarrierTransfer);
		
		return response;
	}
	
	public ReceiveWarehouseCarrierTransferResponse receive(Long idWarehouse, Long idCarrier, Long id, @NonNull ReceiveWarehouseCarrierTransferRequest request) throws NotFoundException, InvalidException {
		Optional<Carrier> optionalCarrier = carrierRepository.findById(idCarrier);
		
		if (!optionalCarrier.isPresent()) {
			throw new NotFoundException(CodeExceptions.CARRIER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.CARRIER_NOT_FOUND));
		}
		
		Carrier carrier = optionalCarrier.get();
		
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		Optional<WarehouseCarrier> optionalWarehouseCarrier = warehouseCarrierRepository.findByWarehouseAndCarrier(warehouse, carrier);
		
		if (!optionalWarehouseCarrier.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_CARRIER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_NOT_FOUND));
		}
		
		WarehouseCarrier warehouseCarrier = optionalWarehouseCarrier.get();
		
		Optional<User> optionalUser = userRepository.findById(request.getDestination().getIdUser());
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();
		
		Optional<WarehouseCarrierTransfer> optionalWarehouseCarrierTransfer = warehouseCarrierTransferRepository.findById(id);
		
		if (!optionalWarehouseCarrierTransfer.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_CARRIER_TRANSFER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_TRANSFER_NOT_FOUND));
		}
		
		WarehouseCarrierTransfer warehouseCarrierTransfer = optionalWarehouseCarrierTransfer.get();
		
		WarehouseCarrierTransferPlace destination = warehouseCarrierTransfer.getDestination();
		destination.setDate(new Date());
		destination.setComments(request.getDestination().getComments());
		destination.setComplete(request.getDestination().getComplete());
		destination.setPartial(request.getDestination().getPartial());
		destination.setPriceByReel(request.getDestination().getPriceByReel());
		destination.setCostByReel(request.getDestination().getCostByReel());
		destination.setCostByMeter(request.getDestination().getCostByMeter());
		destination.setProfit(request.getDestination().getProfit());
		destination.setWarehouse(warehouse);
		destination.setUser(user);
		
		warehouseCarrierTransfer.setStatus(WarehouseCarrierTransfer.Status.RECEIVED);
		
		long onHandComplete = warehouseCarrier.getOnHandComplete() == null ? 0 : warehouseCarrier.getOnHandComplete();
		long onHandPartial = warehouseCarrier.getOnHandPartial() == null ? 0 : warehouseCarrier.getOnHandPartial();
		long maximium = warehouseCarrier.getMaximium();
		onHandComplete += request.getDestination().getComplete() == null ? 0 : request.getDestination().getComplete();
		onHandPartial += request.getDestination().getPartial() == null ? 0 : request.getDestination().getPartial();
		
		if(onHandComplete > maximium) {
			throw new InvalidException(CodeExceptions.WAREHOUSE_CARRIER_MAXIMIUM,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_CARRIER_MAXIMIUM));
		}
		
		warehouseCarrier.setOnHandComplete(onHandComplete);
		warehouseCarrier.setOnHandPartial(onHandPartial);
		
		warehouseCarrierTransferPlaceRepository.save(destination);
		warehouseCarrierTransferRepository.save(warehouseCarrierTransfer);
		warehouseCarrierRepository.save(warehouseCarrier);
		
		ReceiveWarehouseCarrierTransferResponse response = new ReceiveWarehouseCarrierTransferResponse(warehouseCarrierTransfer);
		
		return response;
	}
}
