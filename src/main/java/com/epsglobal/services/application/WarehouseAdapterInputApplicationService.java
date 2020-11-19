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
import com.epsglobal.services.dataaccess.WarehouseAdapterInputRepository;
import com.epsglobal.services.dataaccess.WarehouseAdapterRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.adapter.input.AddWarehouseAdapterInputRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.input.AddWarehouseAdapterInputResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.input.GetWarehouseAdapterInputResponse;
import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseAdapter;
import com.epsglobal.services.domain.WarehouseAdapterInput;

import lombok.NonNull;

@Service
public class WarehouseAdapterInputApplicationService {
	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private AdapterRepository adapterRepository;

	@Autowired
	private WarehouseAdapterRepository warehouseAdapterRepository;

	@Autowired
	private WarehouseAdapterInputRepository warehouseAdapterInputRepository;

	@Autowired
	private UserRepository userRepository;

	public List<GetWarehouseAdapterInputResponse> findAll(Long idWarehouse, Long idAdapter) {
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

		return warehouseAdapter.getWarehouseAdapterInputs().stream()
				.sorted(Comparator.comparing(WarehouseAdapterInput::getId))
				.map(warehouseAdapterInput -> new GetWarehouseAdapterInputResponse(warehouseAdapterInput))
				.collect(Collectors.toList());
	}

	public AddWarehouseAdapterInputResponse add(Long idWarehouse, Long idAdapter,
			@NonNull AddWarehouseAdapterInputRequest request) throws NotFoundException, InvalidException {
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

		Optional<User> optionalUser = userRepository.findById(request.getIdUser());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}

		User user = optionalUser.get();
		WarehouseAdapter warehouseAdapter = optionalWarehouseAdapter.get();

		long onHand = warehouseAdapter.getOnHand() == null ? 0 : warehouseAdapter.getOnHand();
		onHand += request.getQuantity() == null ? 0 : request.getQuantity();

		WarehouseAdapterInput warehouseAdapterInput = new WarehouseAdapterInput();
		warehouseAdapterInput.setDate(new Date());
		warehouseAdapterInput.setOrder(request.getOrder());
		warehouseAdapterInput.setComments(request.getComments());
		warehouseAdapterInput.setQuantity(request.getQuantity());
		warehouseAdapterInput.setCost(request.getCost());
		warehouseAdapterInput.setUser(user);
		warehouseAdapterInput.setWarehouseAdapter(warehouseAdapter);

		warehouseAdapter.setOnHand(onHand);
		warehouseAdapter.getWarehouseAdapterInputs().add(warehouseAdapterInput);

		warehouseAdapterInputRepository.save(warehouseAdapterInput);
		warehouseAdapterRepository.save(warehouseAdapter);

		AddWarehouseAdapterInputResponse response = new AddWarehouseAdapterInputResponse(warehouseAdapterInput);

		return response;
	}
}
