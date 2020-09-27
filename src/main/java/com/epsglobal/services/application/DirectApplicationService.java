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
import com.epsglobal.services.dataaccess.FileRepository;
import com.epsglobal.services.dataaccess.ManufacterRepository;
import com.epsglobal.services.datatransfer.direct.AddDirectRequest;
import com.epsglobal.services.datatransfer.direct.AddDirectResponse;
import com.epsglobal.services.datatransfer.direct.GetDirectResponse;
import com.epsglobal.services.datatransfer.direct.UpdateDirectRequest;
import com.epsglobal.services.datatransfer.direct.UpdateDirectResponse;
import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.Device;
import com.epsglobal.services.domain.Direct;
import com.epsglobal.services.domain.File;
import com.epsglobal.services.domain.Manufacter;

import lombok.NonNull;

@Service
public class DirectApplicationService {
	@Autowired
    private CarrierRepository carrierRepository;
	
	@Autowired
    private DirectRepository directRepository;
	
	@Autowired
    private AdapterRepository adapterRepository;
	
	@Autowired
    private DeviceRepository deviceRepository;
	
	@Autowired
    private FileRepository fileRepository;
	
	@Autowired
    private ManufacterRepository manufacterRepository;
	
	public List<GetDirectResponse> findAll() {
		List<Direct> directs = directRepository.findAll();

		return directs
				.stream()
				.sorted(Comparator.comparing(Direct::getId))
				.map(direct -> new GetDirectResponse(direct))
				.collect(Collectors.toList());
	}
	
	public GetDirectResponse findById(Long id) throws NotFoundException {
		Optional<Direct> optionalDirect = directRepository.findById(id);
		if (!optionalDirect.isPresent()) {
			throw new NotFoundException(CodeExceptions.DIRECT_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DIRECT_NOT_FOUND));
		}
		
		return new GetDirectResponse(optionalDirect.get());
	}
	
	public AddDirectResponse add(@NonNull AddDirectRequest request) throws InvalidException {
		Optional<Carrier> optionalCarrier = carrierRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Direct> optionalDirect = directRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Adapter> optionalAdapter = adapterRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Device> optionalDevice = deviceRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		
		if(optionalCarrier.isPresent() || optionalDirect.isPresent() || optionalAdapter.isPresent()|| optionalDevice.isPresent()) {
			throw new InvalidException(CodeExceptions.DIRECT_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.DIRECT_EXISTS));
		}
		
		Direct direct = new Direct();
		
		direct.setDescription(request.getDescription());
		direct.setManufacterPartNumber(request.getManufacterPartNumber());
		direct.setPrice(request.getPrice());
		direct.setCost(request.getCost());
		direct.setProfit(request.getProfit());
		
		if(request.getIdFile() != null) {
			Optional<File> optionalSpecifications = fileRepository.findById(request.getIdFile());
			direct.setSpecifications(optionalSpecifications.get());
		}
		
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(request.getIdManufacter());
		direct.setManufacter(optionalManufacter.get());
		
		directRepository.save(direct);
		
		AddDirectResponse response = new AddDirectResponse(direct);

		return response;
	}
	
	public UpdateDirectResponse update(long id, @NonNull UpdateDirectRequest request) throws NotFoundException, InvalidException {
		Optional<Direct> optionalDirect = directRepository.findById(id);
		
		if (!optionalDirect.isPresent()) {
			throw new NotFoundException(CodeExceptions.DIRECT_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.DIRECT_NOT_FOUND));
		}
		
		Optional<Carrier> optionalCarrierByManufacter = carrierRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Adapter> optionalAdapterByManufacter = adapterRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Device> optionalDeviceByManufacter = deviceRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		
		if(optionalCarrierByManufacter.isPresent() || optionalAdapterByManufacter.isPresent() || optionalDeviceByManufacter.isPresent()) {
			throw new InvalidException(CodeExceptions.DIRECT_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.DIRECT_EXISTS));
		}
		
		if(!optionalDirect.get().getManufacterPartNumber().equals(request.getManufacterPartNumber())) {
			Optional<Direct> optionalDirectByManufacter = directRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
			
			if(optionalDirectByManufacter.isPresent()) {
				throw new InvalidException(CodeExceptions.DIRECT_EXISTS,
						CodeExceptions.ERRORS.get(CodeExceptions.DIRECT_EXISTS));
			}
		}
		
		Direct direct = optionalDirect.get();
		direct.setDescription(request.getDescription());
		direct.setManufacterPartNumber(request.getManufacterPartNumber());
		direct.setPrice(request.getPrice());
		direct.setCost(request.getCost());
		direct.setProfit(request.getProfit());
		
		if(request.getIdFile() != null) {
			Optional<File> optionalSpecifications = fileRepository.findById(request.getIdFile());
			direct.setSpecifications(optionalSpecifications.get());
		}
		
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(request.getIdManufacter());
		direct.setManufacter(optionalManufacter.get());
		
		directRepository.save(direct);
		
		UpdateDirectResponse response = new UpdateDirectResponse(direct);

		return response;
	}
}
