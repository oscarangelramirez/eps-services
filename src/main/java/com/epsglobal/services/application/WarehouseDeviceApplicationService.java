package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.DeviceRepository;
import com.epsglobal.services.dataaccess.WarehouseDeviceRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.device.GetDeviceResponse;
import com.epsglobal.services.datatransfer.warehouse.device.AddWarehouseDeviceRequest;
import com.epsglobal.services.datatransfer.warehouse.device.AddWarehouseDeviceResponse;
import com.epsglobal.services.datatransfer.warehouse.device.AddWarehouseDevicesRequest;
import com.epsglobal.services.datatransfer.warehouse.device.AddWarehouseDevicesResponse;
import com.epsglobal.services.datatransfer.warehouse.device.GetWarehouseDeviceResponse;
import com.epsglobal.services.datatransfer.warehouse.device.UpdateWarehouseDeviceRequest;
import com.epsglobal.services.datatransfer.warehouse.device.UpdateWarehouseDeviceResponse;
import com.epsglobal.services.domain.Device;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseDevice;

import lombok.NonNull;

@Service
public class WarehouseDeviceApplicationService {
	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private WarehouseDeviceRepository warehouseDeviceRepository;

	public List<GetWarehouseDeviceResponse> findAll(Long idWarehouse) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		Warehouse warehouse = optionalWarehouse.get();
		List<WarehouseDevice> warehouseDevices = warehouseDeviceRepository.findByWarehouse(warehouse);

		return warehouseDevices.stream().sorted(Comparator.comparing(WarehouseDevice::getId))
				.map(warehouseDevice -> new GetWarehouseDeviceResponse(warehouseDevice)).collect(Collectors.toList());
	}

	public GetWarehouseDeviceResponse findById(Long idWarehouse, Long idDevice) throws NotFoundException {
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

		return new GetWarehouseDeviceResponse(optionalWarehouseDevice.get());
	}

	public AddWarehouseDevicesResponse add(Long idWarehouse, @NonNull AddWarehouseDevicesRequest request)
			throws NotFoundException {
		AddWarehouseDevicesResponse response = new AddWarehouseDevicesResponse();

		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		Warehouse warehouse = optionalWarehouse.get();

		for (AddWarehouseDeviceRequest warehouseDeviceRquest : request.getWarehouseDevices()) {
			Optional<Device> optionalDevice = deviceRepository.findById(warehouseDeviceRquest.getIdDevice());

			if (optionalDevice.isPresent()) {
				Device Device = optionalDevice.get();

				WarehouseDevice warehouseDevice = new WarehouseDevice();
				warehouseDevice.setWarehouse(warehouse);
				warehouseDevice.setDevice(Device);

				warehouseDeviceRepository.save(warehouseDevice);

				AddWarehouseDeviceResponse warehouseDeviceResponse = new AddWarehouseDeviceResponse(warehouseDevice);

				response.getWarehouseDevices().add(warehouseDeviceResponse);
			}
		}

		return response;
	}

	public UpdateWarehouseDeviceResponse update(Long idWarehouse, Long idDevice,
			@NonNull UpdateWarehouseDeviceRequest request) throws NotFoundException {
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

		warehouseDeviceRepository.save(warehouseDevice);

		UpdateWarehouseDeviceResponse response = new UpdateWarehouseDeviceResponse(warehouseDevice);

		return response;
	}

	public List<GetDeviceResponse> findAllNotInWarehouse(Long idWarehouse) {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		List<Device> devices = deviceRepository.findAll();

		Warehouse warehouse = optionalWarehouse.get();
		List<WarehouseDevice> warehouseDevices = warehouseDeviceRepository.findByWarehouse(warehouse);

		var devicesNotInWareHouse = devices.stream()
				.filter(c -> !warehouseDevices.stream().anyMatch(w -> w.getDevice().getId().equals(c.getId())));

		return devicesNotInWareHouse.sorted(Comparator.comparing(Device::getId))
				.map(Device -> new GetDeviceResponse(Device)).collect(Collectors.toList());
	}
}
