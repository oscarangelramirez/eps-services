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
import com.epsglobal.services.dataaccess.WarehouseAdapterOutputRepository;
import com.epsglobal.services.dataaccess.WarehouseAdapterRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.adapter.output.AddWarehouseAdapterOutputRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.output.AddWarehouseAdapterOutputResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.output.GetWarehouseAdapterOutputResponse;
import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseAdapter;
import com.epsglobal.services.domain.WarehouseAdapterOutput;

import lombok.NonNull;

@Service
public class WarehouseAdapterOutputApplicationService {
	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private AdapterRepository adapterRepository;

	@Autowired
	private WarehouseAdapterRepository warehouseAdapterRepository;

	@Autowired
	private WarehouseAdapterOutputRepository warehouseAdapterOutputRepository;

	@Autowired
	private UserRepository userRepository;

	public List<GetWarehouseAdapterOutputResponse> findAll(Long idWarehouse, Long idAdapter) {
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

		return warehouseAdapter.getWarehouseAdapterOutputs().stream()
				.sorted(Comparator.comparing(WarehouseAdapterOutput::getId))
				.map(warehouseAdapterInput -> new GetWarehouseAdapterOutputResponse(warehouseAdapterInput))
				.collect(Collectors.toList());
	}

	public AddWarehouseAdapterOutputResponse add(Long idWarehouse, Long idAdapter,
			@NonNull AddWarehouseAdapterOutputRequest request) throws NotFoundException, InvalidException {
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
		onHand -= request.getQuantity() == null ? 0 : request.getQuantity();

		WarehouseAdapterOutput warehouseAdapterOutput = new WarehouseAdapterOutput();
		warehouseAdapterOutput.setDate(new Date());
		warehouseAdapterOutput.setOrder(request.getOrder());
		warehouseAdapterOutput.setComments(request.getComments());
		warehouseAdapterOutput.setQuantity(request.getQuantity());
		warehouseAdapterOutput.setCost(request.getCost());
		warehouseAdapterOutput.setUser(user);

		warehouseAdapter.setOnHand(onHand);
		warehouseAdapter.getWarehouseAdapterOutputs().add(warehouseAdapterOutput);

		warehouseAdapterOutputRepository.save(warehouseAdapterOutput);
		warehouseAdapterRepository.save(warehouseAdapter);

		AddWarehouseAdapterOutputResponse response = new AddWarehouseAdapterOutputResponse(warehouseAdapterOutput);

		return response;
	}
}
