package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.AdapterRepository;
import com.epsglobal.services.dataaccess.CarrierRepository;
import com.epsglobal.services.dataaccess.DeviceRepository;
import com.epsglobal.services.dataaccess.DirectRepository;
import com.epsglobal.services.dataaccess.ManufacterRepository;
import com.epsglobal.services.datatransfer.device.AddDeviceRequest;
import com.epsglobal.services.datatransfer.device.AddDeviceResponse;
import com.epsglobal.services.datatransfer.device.GetDeviceResponse;
import com.epsglobal.services.datatransfer.device.UpdateDeviceRequest;
import com.epsglobal.services.datatransfer.device.UpdateDeviceResponse;
import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.Device;
import com.epsglobal.services.domain.Direct;
import com.epsglobal.services.domain.Manufacter;

import lombok.NonNull;

@Service
public class DeviceApplicationService {
	@Autowired
    private CarrierRepository carrierRepository;
	
	@Autowired
    private DirectRepository directRepository;
	
	@Autowired
    private AdapterRepository adapterRepository;
	
	@Autowired
    private DeviceRepository deviceRepository;
	
	@Autowired
    private ManufacterRepository manufacterRepository;
	
	public List<GetDeviceResponse> findAll() {
		List<Device> devices = deviceRepository.findAll();

		return devices
				.stream()
				.sorted(Comparator.comparing(Device::getId))
				.map(device -> new GetDeviceResponse(device))
				.collect(Collectors.toList());
	}
	
	public GetDeviceResponse findById(Long id) throws NotFoundException {
		Optional<Device> optionalDevice = deviceRepository.findById(id);
		
		if (!optionalDevice.isPresent()) {
			throw new NotFoundException(CodeExceptions.DEVICE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DEVICE_NOT_FOUND));
		}
		
		return new GetDeviceResponse(optionalDevice.get());
	}
	
	public AddDeviceResponse add(@NonNull AddDeviceRequest request) throws InvalidException {
		Optional<Carrier> optionalCarrier = carrierRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Direct> optionalDirect = directRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Adapter> optionalAdapter = adapterRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Device> optionalDevice = deviceRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		
		if(optionalCarrier.isPresent() || optionalDirect.isPresent() || optionalAdapter.isPresent()|| optionalDevice.isPresent()) {
			throw new InvalidException(CodeExceptions.DEVICE_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.DEVICE_EXISTS));
		}
		
		Device device = new Device();
		
		device.setCustomer(request.getCustomer());
		device.setManufacterPartNumber(request.getManufacterPartNumber());
		device.setBlankPartNumber(request.getBlankPartNumber());
		
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(request.getIdManufacter());
		device.setManufacter(optionalManufacter.get());
		
		deviceRepository.save(device);
		
		AddDeviceResponse response = new AddDeviceResponse(device);

		return response;
	}
	
	public UpdateDeviceResponse update(long id, @NonNull UpdateDeviceRequest request) throws NotFoundException, InvalidException {
		Optional<Device> optionalDevice = deviceRepository.findById(id);
		
		if (!optionalDevice.isPresent()) {
			throw new NotFoundException(CodeExceptions.DEVICE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DEVICE_NOT_FOUND));
		}
		
		Optional<Carrier> optionalCarrierByManufacter = carrierRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Direct> optionalDirectByManufacter = directRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Adapter> optionalAdapterByManufacter = adapterRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		
		if(optionalCarrierByManufacter.isPresent() || optionalDirectByManufacter.isPresent() || optionalAdapterByManufacter.isPresent()) {
			throw new InvalidException(CodeExceptions.DEVICE_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.DEVICE_EXISTS));
		}
		
		if(!optionalDevice.get().getManufacterPartNumber().equals(request.getManufacterPartNumber())) {
			Optional<Device> optionalDeviceByManufacter = deviceRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
			
			if(optionalDeviceByManufacter.isPresent()) {
				throw new InvalidException(CodeExceptions.DEVICE_EXISTS,
						CodeExceptions.ERRORS.get(CodeExceptions.DEVICE_EXISTS));
			}
		}
		
		Device device = optionalDevice.get();
		device.setCustomer(request.getCustomer());
		device.setManufacterPartNumber(request.getManufacterPartNumber());
		device.setBlankPartNumber(request.getBlankPartNumber());
		
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(request.getIdManufacter());
		device.setManufacter(optionalManufacter.get());
		
		deviceRepository.save(device);
		
		UpdateDeviceResponse response = new UpdateDeviceResponse(device);

		return response;
	}
}
