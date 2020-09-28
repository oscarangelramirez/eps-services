package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.AdapterRepository;
import com.epsglobal.services.dataaccess.WarehouseAdapterRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.adapter.GetAdapterResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.AddWarehouseAdapterRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.AddWarehouseAdapterResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.AddWarehouseAdaptersRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.AddWarehouseAdaptersResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.GetWarehouseAdapterResponse;
import com.epsglobal.services.datatransfer.warehouse.adapter.UpdateWarehouseAdapterRequest;
import com.epsglobal.services.datatransfer.warehouse.adapter.UpdateWarehouseAdapterResponse;
import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseAdapter;

import lombok.NonNull;

@Service
public class WarehouseAdapterApplicationService {
	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private AdapterRepository adapterRepository;

	@Autowired
	private WarehouseAdapterRepository warehouseAdapterRepository;

	public List<GetWarehouseAdapterResponse> findAll(Long idWarehouse) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		Warehouse warehouse = optionalWarehouse.get();
		List<WarehouseAdapter> warehouseAdapters = warehouseAdapterRepository.findByWarehouse(warehouse);

		return warehouseAdapters.stream().sorted(Comparator.comparing(WarehouseAdapter::getId))
				.map(warehouseAdapter -> new GetWarehouseAdapterResponse(warehouseAdapter))
				.collect(Collectors.toList());
	}

	public GetWarehouseAdapterResponse findById(Long idWarehouse, Long idAdapter) throws NotFoundException {
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

		return new GetWarehouseAdapterResponse(optionalWarehouseAdapter.get());
	}

	public AddWarehouseAdaptersResponse add(Long idWarehouse, @NonNull AddWarehouseAdaptersRequest request)
			throws NotFoundException {
		AddWarehouseAdaptersResponse response = new AddWarehouseAdaptersResponse();

		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		Warehouse warehouse = optionalWarehouse.get();

		for (AddWarehouseAdapterRequest warehouseAdapterRquest : request.getWarehouseAdapters()) {
			Optional<Adapter> optionalAdapter = adapterRepository.findById(warehouseAdapterRquest.getIdAdapter());

			if (optionalAdapter.isPresent()) {
				Adapter Adapter = optionalAdapter.get();

				WarehouseAdapter warehouseAdapter = new WarehouseAdapter();
				warehouseAdapter.setWarehouse(warehouse);
				warehouseAdapter.setAdapter(Adapter);

				warehouseAdapterRepository.save(warehouseAdapter);

				AddWarehouseAdapterResponse warehouseAdapterResponse = new AddWarehouseAdapterResponse(
						warehouseAdapter);

				response.getWarehouseAdapters().add(warehouseAdapterResponse);
			}
		}

		return response;
	}

	public UpdateWarehouseAdapterResponse update(Long idWarehouse, Long idAdapter,
			@NonNull UpdateWarehouseAdapterRequest request) throws NotFoundException {
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

		warehouseAdapterRepository.save(warehouseAdapter);

		UpdateWarehouseAdapterResponse response = new UpdateWarehouseAdapterResponse(warehouseAdapter);

		return response;
	}

	public List<GetAdapterResponse> findAllNotInWarehouse(Long idWarehouse) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		List<Adapter> adapters = adapterRepository.findAll();

		Warehouse warehouse = optionalWarehouse.get();
		List<WarehouseAdapter> warehouseAdapters = warehouseAdapterRepository.findByWarehouse(warehouse);

		var adaptersNotInWareHouse = adapters.stream()
				.filter(c -> !warehouseAdapters.stream().anyMatch(w -> w.getAdapter().getId().equals(c.getId())));

		return adaptersNotInWareHouse.sorted(Comparator.comparing(Adapter::getId))
				.map(Adapter -> new GetAdapterResponse(Adapter)).collect(Collectors.toList());
	}
}
