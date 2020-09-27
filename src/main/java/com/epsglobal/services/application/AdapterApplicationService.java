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
import com.epsglobal.services.datatransfer.adapter.AddAdapterRequest;
import com.epsglobal.services.datatransfer.adapter.AddAdapterResponse;
import com.epsglobal.services.datatransfer.adapter.GetAdapterResponse;
import com.epsglobal.services.datatransfer.adapter.UpdateAdapterRequest;
import com.epsglobal.services.datatransfer.adapter.UpdateAdapterResponse;
import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.Device;
import com.epsglobal.services.domain.Direct;
import com.epsglobal.services.domain.Manufacter;

import lombok.NonNull;

@Service
public class AdapterApplicationService {
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
	
	public List<GetAdapterResponse> findAll() {
		List<Adapter> adapters = adapterRepository.findAll();

		return adapters
				.stream()
				.sorted(Comparator.comparing(Adapter::getId))
				.map(adapter -> new GetAdapterResponse(adapter))
				.collect(Collectors.toList());
	}
	
	public GetAdapterResponse findById(Long id) throws NotFoundException {
		Optional<Adapter> optionalAdapter = adapterRepository.findById(id);
		
		if (!optionalAdapter.isPresent()) {
			throw new NotFoundException(CodeExceptions.ADAPTER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ADAPTER_NOT_FOUND));
		}
		
		return new GetAdapterResponse(optionalAdapter.get());
	}
	
	public AddAdapterResponse add(@NonNull AddAdapterRequest request) throws InvalidException {
		Optional<Carrier> optionalCarrier = carrierRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Direct> optionalDirect = directRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Adapter> optionalAdapter = adapterRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Device> optionalDevice = deviceRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		
		if(optionalCarrier.isPresent() || optionalDirect.isPresent() || optionalAdapter.isPresent()|| optionalDevice.isPresent()) {
			throw new InvalidException(CodeExceptions.ADAPTER_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.ADAPTER_EXISTS));
		}
		
		Adapter adapter = new Adapter();
		
		adapter.setManufacterPartNumber(request.getManufacterPartNumber());
		adapter.setInternalPartNumber(request.getInternalPartNumber());
		adapter.setSerial(request.getSerial());
		adapter.setIsActive(request.getIsActive());
		adapter.setCost(request.getCost());
		
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(request.getIdManufacter());
		adapter.setManufacter(optionalManufacter.get());
		
		adapterRepository.save(adapter);
		
		AddAdapterResponse response = new AddAdapterResponse(adapter);

		return response;
	}
	
	public UpdateAdapterResponse update(long id, @NonNull UpdateAdapterRequest request) throws NotFoundException, InvalidException  {
		Optional<Adapter> optionalAdapter = adapterRepository.findById(id);
		
		if (!optionalAdapter.isPresent()) {
			throw new NotFoundException(CodeExceptions.ADAPTER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ADAPTER_NOT_FOUND));
		}
		
		Optional<Carrier> optionalCarrierByManufacter = carrierRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Direct> optionalDirectByManufacter = directRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Device> optionalDeviceByManufacter = deviceRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		
		if(optionalCarrierByManufacter.isPresent() || optionalDirectByManufacter.isPresent() || optionalDeviceByManufacter.isPresent()) {
			throw new InvalidException(CodeExceptions.ADAPTER_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.ADAPTER_EXISTS));
		}
		
		if(!optionalAdapter.get().getManufacterPartNumber().equals(request.getManufacterPartNumber())) {
			Optional<Adapter> optionalAdapterByManufacter = adapterRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
			
			if(optionalAdapterByManufacter.isPresent()) {
				throw new InvalidException(CodeExceptions.ADAPTER_EXISTS,
						CodeExceptions.ERRORS.get(CodeExceptions.ADAPTER_EXISTS));
			}
		}
		
		Adapter adapter = optionalAdapter.get();
		adapter.setManufacterPartNumber(request.getManufacterPartNumber());
		adapter.setInternalPartNumber(request.getInternalPartNumber());
		adapter.setSerial(request.getSerial());
		adapter.setIsActive(request.getIsActive());
		adapter.setCost(request.getCost());
		
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(request.getIdManufacter());
		adapter.setManufacter(optionalManufacter.get());
		
		adapterRepository.save(adapter);
		
		UpdateAdapterResponse response = new UpdateAdapterResponse(adapter);

		return response;
	}
}
