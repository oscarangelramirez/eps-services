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
import com.epsglobal.services.dataaccess.AdapterRepository;
import com.epsglobal.services.dataaccess.UserRepository;
import com.epsglobal.services.dataaccess.WarehouseAdapterRepository;
import com.epsglobal.services.dataaccess.WarehouseAdapterTransferPlaceRepository;
import com.epsglobal.services.dataaccess.WarehouseAdapterTransferRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.GetWarehouseAdapterTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.ReceiveWarehouseAdapterTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.ReceiveWarehouseAdapterTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.SendWarehouseAdapterTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.transfer.SendWarehouseAdapterTransferResponse;
import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseAdapter;
import com.epsglobal.services.domain.WarehouseAdapterTransfer;
import com.epsglobal.services.domain.WarehouseAdapterTransferPlace;

import lombok.NonNull;

@Service
public class WarehouseAdapterTransferApplicationService {
	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private AdapterRepository adapterRepository;

	@Autowired
	private WarehouseAdapterRepository warehouseAdapterRepository;

	@Autowired
	private WarehouseAdapterTransferRepository warehouseAdapterTransferRepository;

	@Autowired
	private WarehouseAdapterTransferPlaceRepository warehouseAdapterTransferPlaceRepository;

	@Autowired
	private UserRepository userRepository;

	public List<GetWarehouseAdapterTransferResponse> findAll(Long idWarehouse, Long idAdapter) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		Optional<Adapter> optionalAdapter = adapterRepository.findById(idAdapter);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		if (!optionalAdapter.isPresent()) {
			throw new NotFoundException(CodeExceptions.ADAPTER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ADAPTER_NOT_FOUND));
		}

		Warehouse warehouse = optionalWarehouse.get();
		Adapter adapter = optionalAdapter.get();

		Optional<WarehouseAdapter> optionalWarehouseAdapter = warehouseAdapterRepository
				.findByWarehouseAndAdapter(warehouse, adapter);

		if (!optionalWarehouseAdapter.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_ADAPTER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_ADAPTER_NOT_FOUND));
		}

		WarehouseAdapter warehouseAdapter = optionalWarehouseAdapter.get();

		return warehouseAdapter.getWarehouseAdapterTransfers().stream()
				.sorted(Comparator.comparing(WarehouseAdapterTransfer::getId))
				.map(warehouseAdapterTransfer -> new GetWarehouseAdapterTransferResponse(warehouseAdapterTransfer))
				.collect(Collectors.toList());
	}

	public GetWarehouseAdapterTransferResponse findById(Long id) {
		Optional<WarehouseAdapterTransfer> optionalWarehouseAdapterTransfer = warehouseAdapterTransferRepository
				.findById(id);

		if (!optionalWarehouseAdapterTransfer.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_ADAPTER_TRANSFER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_ADAPTER_TRANSFER_NOT_FOUND));
		}

		return new GetWarehouseAdapterTransferResponse(optionalWarehouseAdapterTransfer.get());
	}

	public SendWarehouseAdapterTransferResponse send(Long idWarehouse, Long idAdapter,
			@NonNull SendWarehouseAdapterTransferRequest request) throws NotFoundException, InvalidException {
		Optional<Adapter> optionalAdapter = adapterRepository.findById(idAdapter);

		if (!optionalAdapter.isPresent()) {
			throw new NotFoundException(CodeExceptions.ADAPTER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ADAPTER_NOT_FOUND));
		}

		Adapter adapter = optionalAdapter.get();

		Optional<Warehouse> optionalWarehouseOrigin = warehouseRepository
				.findById(request.getOrigin().getIdWarehouse());

		if (!optionalWarehouseOrigin.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		Optional<Warehouse> optionalWarehouseDestination = warehouseRepository
				.findById(request.getDestination().getIdWarehouse());

		if (!optionalWarehouseDestination.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		Warehouse warehouseOrigin = optionalWarehouseOrigin.get();
		Optional<WarehouseAdapter> optionalWarehouseAdapterOrigin = warehouseAdapterRepository
				.findByWarehouseAndAdapter(warehouseOrigin, adapter);

		if (!optionalWarehouseAdapterOrigin.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_ADAPTER_ORIGIN_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_ADAPTER_ORIGIN_NOT_FOUND));
		}

		WarehouseAdapter warehouseAdapterOrigin = optionalWarehouseAdapterOrigin.get();

		Warehouse warehouseDestination = optionalWarehouseDestination.get();
		Optional<WarehouseAdapter> optionalWarehouseAdapterDestination = warehouseAdapterRepository
				.findByWarehouseAndAdapter(warehouseDestination, adapter);

		if (!optionalWarehouseAdapterDestination.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_ADAPTER_DESTINATION_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_ADAPTER_DESTINATION_NOT_FOUND));
		}

		WarehouseAdapter warehouseAdapterDestination = optionalWarehouseAdapterDestination.get();

		Optional<User> optionalUser = userRepository.findById(request.getOrigin().getIdUser());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}

		User user = optionalUser.get();

		WarehouseAdapterTransferPlace origin = new WarehouseAdapterTransferPlace();
		origin.setDate(new Date());
		origin.setComments(request.getOrigin().getComments());
		origin.setQuantity(request.getOrigin().getQuantity());
		origin.setCost(request.getOrigin().getCost());
		origin.setWarehouse(warehouseOrigin);
		origin.setAdapter(adapter);
		origin.setUser(user);

		WarehouseAdapterTransferPlace destination = new WarehouseAdapterTransferPlace();
		destination.setDate(null);
		destination.setComments(null);
		destination.setQuantity(null);
		destination.setCost(request.getDestination().getCost());
		destination.setWarehouse(warehouseDestination);
		destination.setAdapter(adapter);
		destination.setUser(null);

		WarehouseAdapterTransfer warehouseAdapterTransfer = new WarehouseAdapterTransfer();
		warehouseAdapterTransfer.setStatus(WarehouseAdapterTransfer.Status.SENT);
		warehouseAdapterTransfer.setOrigin(origin);
		warehouseAdapterTransfer.setDestination(destination);

		long onHand = warehouseAdapterOrigin.getOnHand() == null ? 0 : warehouseAdapterOrigin.getOnHand();
		onHand -= request.getOrigin().getQuantity() == null ? 0 : request.getOrigin().getQuantity();

		warehouseAdapterOrigin.setOnHand(onHand);

		warehouseAdapterTransferPlaceRepository.save(origin);
		warehouseAdapterTransferPlaceRepository.save(destination);
		warehouseAdapterTransferRepository.save(warehouseAdapterTransfer);

		warehouseAdapterOrigin.getWarehouseAdapterTransfers().add(warehouseAdapterTransfer);
		warehouseAdapterDestination.getWarehouseAdapterTransfers().add(warehouseAdapterTransfer);

		warehouseAdapterRepository.save(warehouseAdapterOrigin);
		warehouseAdapterRepository.save(warehouseAdapterDestination);

		SendWarehouseAdapterTransferResponse response = new SendWarehouseAdapterTransferResponse(
				warehouseAdapterTransfer);

		return response;
	}

	public ReceiveWarehouseAdapterTransferResponse receive(Long idWarehouse, Long idAdapter, Long id,
			@NonNull ReceiveWarehouseAdapterTransferRequest request) throws NotFoundException, InvalidException {
		Optional<Adapter> optionalAdapter = adapterRepository.findById(idAdapter);

		if (!optionalAdapter.isPresent()) {
			throw new NotFoundException(CodeExceptions.ADAPTER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ADAPTER_NOT_FOUND));
		}

		Adapter adapter = optionalAdapter.get();

		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		Warehouse warehouse = optionalWarehouse.get();
		Optional<WarehouseAdapter> optionalWarehouseAdapter = warehouseAdapterRepository
				.findByWarehouseAndAdapter(warehouse, adapter);

		if (!optionalWarehouseAdapter.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_ADAPTER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_ADAPTER_NOT_FOUND));
		}

		WarehouseAdapter warehouseAdapter = optionalWarehouseAdapter.get();

		Optional<User> optionalUser = userRepository.findById(request.getDestination().getIdUser());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}

		User user = optionalUser.get();

		Optional<WarehouseAdapterTransfer> optionalWarehouseAdapterTransfer = warehouseAdapterTransferRepository
				.findById(id);

		if (!optionalWarehouseAdapterTransfer.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_ADAPTER_TRANSFER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_ADAPTER_TRANSFER_NOT_FOUND));
		}

		WarehouseAdapterTransfer warehouseAdapterTransfer = optionalWarehouseAdapterTransfer.get();

		WarehouseAdapterTransferPlace destination = warehouseAdapterTransfer.getDestination();
		destination.setDate(new Date());
		destination.setComments(request.getDestination().getComments());
		destination.setQuantity(request.getDestination().getQuantity());
		destination.setCost(request.getDestination().getCost());
		destination.setWarehouse(warehouse);
		destination.setUser(user);

		warehouseAdapterTransfer.setStatus(WarehouseAdapterTransfer.Status.RECEIVED);

		long onHand = warehouseAdapter.getOnHand() == null ? 0 : warehouseAdapter.getOnHand();
		onHand += request.getDestination().getQuantity() == null ? 0 : request.getDestination().getQuantity();

		warehouseAdapter.setOnHand(onHand);

		warehouseAdapterTransferPlaceRepository.save(destination);
		warehouseAdapterTransferRepository.save(warehouseAdapterTransfer);
		warehouseAdapterRepository.save(warehouseAdapter);

		ReceiveWarehouseAdapterTransferResponse response = new ReceiveWarehouseAdapterTransferResponse(
				warehouseAdapterTransfer);

		return response;
	}
}
