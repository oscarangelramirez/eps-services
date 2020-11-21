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
import com.epsglobal.services.dataaccess.WarehouseDeviceRepository;
import com.epsglobal.services.dataaccess.WarehouseDeviceTransferPlaceRepository;
import com.epsglobal.services.dataaccess.WarehouseDeviceTransferRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.GetWarehouseDeviceTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.ReceiveWarehouseDeviceTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.ReceiveWarehouseDeviceTransferResponse;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.SendWarehouseDeviceTransferRequest;
import com.epsglobal.services.datatransfer.warehouse.device.transfer.SendWarehouseDeviceTransferResponse;
import com.epsglobal.services.domain.Device;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseDevice;
import com.epsglobal.services.domain.WarehouseDeviceTransfer;
import com.epsglobal.services.domain.WarehouseDeviceTransferPlace;

import lombok.NonNull;

@Service
public class WarehouseDeviceTransferApplicationService {
	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private WarehouseDeviceRepository warehouseDeviceRepository;

	@Autowired
	private WarehouseDeviceTransferRepository warehouseDeviceTransferRepository;

	@Autowired
	private WarehouseDeviceTransferPlaceRepository warehouseDeviceTransferPlaceRepository;

	@Autowired
	private UserRepository userRepository;

	public List<GetWarehouseDeviceTransferResponse> findAll(Long idWarehouse, Long idDevice) {
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

		return warehouseDevice.getWarehouseDeviceTransfers().stream()
				.sorted(Comparator.comparing(WarehouseDeviceTransfer::getId))
				.map(warehouseDeviceTransfer -> new GetWarehouseDeviceTransferResponse(warehouseDeviceTransfer))
				.collect(Collectors.toList());
	}

	public GetWarehouseDeviceTransferResponse findById(Long id) {
		Optional<WarehouseDeviceTransfer> optionalWarehouseDeviceTransfer = warehouseDeviceTransferRepository
				.findById(id);

		if (!optionalWarehouseDeviceTransfer.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DEVICE_TRANSFER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DEVICE_TRANSFER_NOT_FOUND));
		}

		return new GetWarehouseDeviceTransferResponse(optionalWarehouseDeviceTransfer.get());
	}

	public SendWarehouseDeviceTransferResponse send(Long idWarehouse, Long idDevice,
			@NonNull SendWarehouseDeviceTransferRequest request) throws NotFoundException, InvalidException {
		Optional<Device> optionalDevice = deviceRepository.findById(idDevice);

		if (!optionalDevice.isPresent()) {
			throw new NotFoundException(CodeExceptions.DEVICE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DEVICE_NOT_FOUND));
		}

		Device device = optionalDevice.get();

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
		Optional<WarehouseDevice> optionalWarehouseDeviceOrigin = warehouseDeviceRepository
				.findByWarehouseAndDevice(warehouseOrigin, device);

		if (!optionalWarehouseDeviceOrigin.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DEVICE_ORIGIN_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DEVICE_ORIGIN_NOT_FOUND));
		}

		WarehouseDevice warehouseDeviceOrigin = optionalWarehouseDeviceOrigin.get();

		Warehouse warehouseDestination = optionalWarehouseDestination.get();
		Optional<WarehouseDevice> optionalWarehouseDeviceDestination = warehouseDeviceRepository
				.findByWarehouseAndDevice(warehouseDestination, device);

		if (!optionalWarehouseDeviceDestination.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DEVICE_DESTINATION_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DEVICE_DESTINATION_NOT_FOUND));
		}

		WarehouseDevice warehouseDeviceDestination = optionalWarehouseDeviceDestination.get();

		Optional<User> optionalUser = userRepository.findById(request.getOrigin().getIdUser());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}

		User user = optionalUser.get();

		WarehouseDeviceTransferPlace origin = new WarehouseDeviceTransferPlace();
		origin.setDate(new Date());
		origin.setComments(request.getOrigin().getComments());
		origin.setQuantity(request.getOrigin().getQuantity());
		origin.setWarehouse(warehouseOrigin);
		origin.setDevice(device);
		origin.setUser(user);

		WarehouseDeviceTransferPlace destination = new WarehouseDeviceTransferPlace();
		destination.setDate(null);
		destination.setComments(null);
		destination.setQuantity(null);
		destination.setWarehouse(warehouseDestination);
		destination.setDevice(device);
		destination.setUser(null);

		WarehouseDeviceTransfer warehouseDeviceTransfer = new WarehouseDeviceTransfer();
		warehouseDeviceTransfer.setStatus(WarehouseDeviceTransfer.Status.SENT);
		warehouseDeviceTransfer.setOrigin(origin);
		warehouseDeviceTransfer.setDestination(destination);

		long onHand = warehouseDeviceOrigin.getOnHand() == null ? 0 : warehouseDeviceOrigin.getOnHand();
		onHand -= request.getOrigin().getQuantity() == null ? 0 : request.getOrigin().getQuantity();

		warehouseDeviceOrigin.setOnHand(onHand);

		warehouseDeviceTransferPlaceRepository.save(origin);
		warehouseDeviceTransferPlaceRepository.save(destination);
		warehouseDeviceTransferRepository.save(warehouseDeviceTransfer);

		warehouseDeviceOrigin.getWarehouseDeviceTransfers().add(warehouseDeviceTransfer);
		warehouseDeviceDestination.getWarehouseDeviceTransfers().add(warehouseDeviceTransfer);

		warehouseDeviceRepository.save(warehouseDeviceOrigin);
		warehouseDeviceRepository.save(warehouseDeviceDestination);

		SendWarehouseDeviceTransferResponse response = new SendWarehouseDeviceTransferResponse(warehouseDeviceTransfer);

		return response;
	}

	public ReceiveWarehouseDeviceTransferResponse receive(Long idWarehouse, Long idDevice, Long id,
			@NonNull ReceiveWarehouseDeviceTransferRequest request) throws NotFoundException, InvalidException {
		Optional<Device> optionalDevice = deviceRepository.findById(idDevice);

		if (!optionalDevice.isPresent()) {
			throw new NotFoundException(CodeExceptions.DEVICE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DEVICE_NOT_FOUND));
		}

		Device device = optionalDevice.get();

		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);

		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}

		Warehouse warehouse = optionalWarehouse.get();
		Optional<WarehouseDevice> optionalWarehouseDevice = warehouseDeviceRepository
				.findByWarehouseAndDevice(warehouse, device);

		if (!optionalWarehouseDevice.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DEVICE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DEVICE_NOT_FOUND));
		}

		WarehouseDevice warehouseDevice = optionalWarehouseDevice.get();

		Optional<User> optionalUser = userRepository.findById(request.getDestination().getIdUser());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}

		User user = optionalUser.get();

		Optional<WarehouseDeviceTransfer> optionalWarehouseDeviceTransfer = warehouseDeviceTransferRepository
				.findById(id);

		if (!optionalWarehouseDeviceTransfer.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_DEVICE_TRANSFER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_DEVICE_TRANSFER_NOT_FOUND));
		}

		WarehouseDeviceTransfer warehouseDeviceTransfer = optionalWarehouseDeviceTransfer.get();

		WarehouseDeviceTransferPlace destination = warehouseDeviceTransfer.getDestination();
		destination.setDate(new Date());
		destination.setComments(request.getDestination().getComments());
		destination.setQuantity(request.getDestination().getQuantity());
		destination.setWarehouse(warehouse);
		destination.setUser(user);

		warehouseDeviceTransfer.setStatus(WarehouseDeviceTransfer.Status.RECEIVED);

		long onHand = warehouseDevice.getOnHand() == null ? 0 : warehouseDevice.getOnHand();
		onHand += request.getDestination().getQuantity() == null ? 0 : request.getDestination().getQuantity();

		warehouseDevice.setOnHand(onHand);

		warehouseDeviceTransferPlaceRepository.save(destination);
		warehouseDeviceTransferRepository.save(warehouseDeviceTransfer);
		warehouseDeviceRepository.save(warehouseDevice);

		ReceiveWarehouseDeviceTransferResponse response = new ReceiveWarehouseDeviceTransferResponse(
				warehouseDeviceTransfer);

		return response;
	}
}
