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
import com.epsglobal.services.dataaccess.DeviceRepository;
import com.epsglobal.services.dataaccess.UserRepository;
import com.epsglobal.services.dataaccess.WarehouseDeviceOutputRepository;
import com.epsglobal.services.dataaccess.WarehouseDeviceRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.device.output.AddWarehouseDeviceOutputRequest;
import com.epsglobal.services.datatransfer.warehouse.device.output.AddWarehouseDeviceOutputResponse;
import com.epsglobal.services.datatransfer.warehouse.device.output.GetWarehouseDeviceOutputResponse;
import com.epsglobal.services.domain.Device;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseDevice;
import com.epsglobal.services.domain.WarehouseDeviceOutput;

import lombok.NonNull;

@Service
public class WarehouseDeviceOutputApplicationService {
	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private WarehouseDeviceRepository warehouseDeviceRepository;

	@Autowired
	private WarehouseDeviceOutputRepository warehouseDeviceOutputRepository;

	@Autowired
	private UserRepository userRepository;

	public List<GetWarehouseDeviceOutputResponse> findAll(Long idWarehouse, Long idDevice) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		Optional<Device> optionalDevice = deviceRepository.findById(idDevice);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		if (!optionalDevice.isPresent()) {
			throw new NotFoundException(CodeExceptions.DEVICE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DEVICE_NOT_FOUND));
		}

		Warehouse warehouse = optionalWarehouse.get();
		Device device = optionalDevice.get();

		Optional<WarehouseDevice> optionalWarehouseDevice = warehouseDeviceRepository
				.findByWarehouseAndDevice(warehouse, device);

		if (!optionalWarehouseDevice.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DEVICE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DEVICE_NOT_FOUND));
		}

		WarehouseDevice warehouseDevice = optionalWarehouseDevice.get();

		return warehouseDevice.getWarehouseDeviceOutputs().stream()
				.sorted(Comparator.comparing(WarehouseDeviceOutput::getId))
				.map(warehouseDeviceInput -> new GetWarehouseDeviceOutputResponse(warehouseDeviceInput))
				.collect(Collectors.toList());
	}

	public AddWarehouseDeviceOutputResponse add(Long idWarehouse, Long idDevice,
			@NonNull AddWarehouseDeviceOutputRequest request) throws NotFoundException, InvalidException {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		Optional<Device> optionalDevice = deviceRepository.findById(idDevice);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		if (!optionalDevice.isPresent()) {
			throw new NotFoundException(CodeExceptions.DEVICE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DEVICE_NOT_FOUND));
		}

		Warehouse warehouse = optionalWarehouse.get();
		Device device = optionalDevice.get();

		Optional<WarehouseDevice> optionalWarehouseDevice = warehouseDeviceRepository
				.findByWarehouseAndDevice(warehouse, device);

		if (!optionalWarehouseDevice.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DEVICE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DEVICE_NOT_FOUND));
		}

		Optional<User> optionalUser = userRepository.findById(request.getIdUser());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}

		User user = optionalUser.get();
		WarehouseDevice warehouseDevice = optionalWarehouseDevice.get();

		long onHand = warehouseDevice.getOnHand() == null ? 0 : warehouseDevice.getOnHand();
		onHand -= request.getQuantity() == null ? 0 : request.getQuantity();

		WarehouseDeviceOutput warehouseDeviceOutput = new WarehouseDeviceOutput();
		warehouseDeviceOutput.setDate(new Date());
		warehouseDeviceOutput.setOrder(request.getOrder());
		warehouseDeviceOutput.setComments(request.getComments());
		warehouseDeviceOutput.setQuantity(request.getQuantity());
		warehouseDeviceOutput.setUser(user);

		warehouseDevice.setOnHand(onHand);
		warehouseDevice.getWarehouseDeviceOutputs().add(warehouseDeviceOutput);

		warehouseDeviceOutputRepository.save(warehouseDeviceOutput);
		warehouseDeviceRepository.save(warehouseDevice);

		AddWarehouseDeviceOutputResponse response = new AddWarehouseDeviceOutputResponse(warehouseDeviceOutput);

		return response;
	}
}
