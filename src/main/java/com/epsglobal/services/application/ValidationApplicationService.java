package com.epsglobal.services.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.dataaccess.AdapterRepository;
import com.epsglobal.services.dataaccess.CarrierRepository;
import com.epsglobal.services.dataaccess.DeviceRepository;
import com.epsglobal.services.dataaccess.DirectRepository;
import com.epsglobal.services.datatransfer.validation.ValidationManufacterPartNumberRequest;
import com.epsglobal.services.datatransfer.validation.ValidationResponse;
import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.Device;
import com.epsglobal.services.domain.Direct;

@Service
public class ValidationApplicationService {
	@Autowired
    private CarrierRepository carrierRepository;
	
	@Autowired
    private DirectRepository directRepository;
	
	@Autowired
    private AdapterRepository adapterRepository;
	
	@Autowired
    private DeviceRepository deviceRepository;
	
	public ValidationResponse validateManufacterPartNumber(ValidationManufacterPartNumberRequest request) {
		ValidationResponse response = new ValidationResponse();

		Optional<Carrier> optionalCarrier = carrierRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Direct> optionalDirect = directRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Adapter> optionalAdapter = adapterRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Device> optionalDevice = deviceRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		
		if(optionalCarrier.isPresent() || optionalDirect.isPresent() || optionalAdapter.isPresent()|| optionalDevice.isPresent()) {
			response.setIsValid(false);
			response.setMessage("The manufacter part number already exists");
		}
		else {
			response.setIsValid(true);
			response.setMessage("");
		}
		
		return response;
	}
}
