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
import com.epsglobal.services.datatransfer.carrier.AddCarrierRequest;
import com.epsglobal.services.datatransfer.carrier.AddCarrierResponse;
import com.epsglobal.services.datatransfer.carrier.GetCarrierResponse;
import com.epsglobal.services.datatransfer.carrier.UpdateCarrierRequest;
import com.epsglobal.services.datatransfer.carrier.UpdateCarrierResponse;
import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.Device;
import com.epsglobal.services.domain.Direct;
import com.epsglobal.services.domain.File;
import com.epsglobal.services.domain.Manufacter;

import lombok.NonNull;

@Service
public class CarrierApplicationService {
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
	
	public List<GetCarrierResponse> findAll() {
		List<Carrier> carriers = carrierRepository.findAll();

		return carriers
				.stream()
				.sorted(Comparator.comparing(Carrier::getId))
				.map(carrier -> new GetCarrierResponse(carrier))
				.collect(Collectors.toList());
	}
	
	public GetCarrierResponse findById(Long id) throws NotFoundException {
		Optional<Carrier> optionalCarrier = carrierRepository.findById(id);
		
		if (!optionalCarrier.isPresent()) {
			throw new NotFoundException(CodeExceptions.CARRIER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.CARRIER_NOT_FOUND));
		}
		
		return new GetCarrierResponse(optionalCarrier.get());
	}
	
	public AddCarrierResponse add(@NonNull AddCarrierRequest request) throws InvalidException {
		Optional<Carrier> optionalCarrier = carrierRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Direct> optionalDirect = directRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Adapter> optionalAdapter = adapterRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Device> optionalDevice = deviceRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		
		if(optionalCarrier.isPresent() || optionalDirect.isPresent() || optionalAdapter.isPresent()|| optionalDevice.isPresent()) {
			throw new InvalidException(CodeExceptions.CARRIER_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.CARRIER_EXISTS));
		}
		
		Carrier carrier = new Carrier();
		
		carrier.setDescription(request.getDescription());
		carrier.setManufacterPartNumber(request.getManufacterPartNumber());
		carrier.setEncapsulated(request.getEncapsulated());
		carrier.setW(request.getW());
		carrier.setA0(request.getA0());
		carrier.setB0(request.getB0());
		carrier.setK0(request.getK0());
		carrier.setP(request.getP());
		carrier.setT(request.getT());
		carrier.setMetersByReel(request.getMetersByReel());
		carrier.setPocketByReel(request.getPocketByReel());
		carrier.setPriceByReel(request.getPriceByReel());
		carrier.setCostByReel(request.getCostByReel());
		carrier.setCostByMeter(request.getCostByMeter());
		carrier.setProfit(request.getProfit());
		
		if(request.getIdFile() != null) {
			Optional<File> optionalDrawing = fileRepository.findById(request.getIdFile());
			carrier.setDrawing(optionalDrawing.get());
		}
		
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(request.getIdManufacter());
		carrier.setManufacter(optionalManufacter.get());
		
		carrierRepository.save(carrier);
		
		AddCarrierResponse response = new AddCarrierResponse(carrier);

		return response;
	}
	
	public UpdateCarrierResponse update(long id, @NonNull UpdateCarrierRequest request) throws NotFoundException, InvalidException {
		Optional<Carrier> optionalCarrier = carrierRepository.findById(id);
		
		if (!optionalCarrier.isPresent()) {
			throw new NotFoundException(CodeExceptions.CARRIER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.CARRIER_NOT_FOUND));
		}
		
		Optional<Direct> optionalDirectByManufacter = directRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Adapter> optionalAdapterByManufacter = adapterRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		Optional<Device> optionalDeviceByManufacter = deviceRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
		
		if(optionalDirectByManufacter.isPresent() || optionalAdapterByManufacter.isPresent() || optionalDeviceByManufacter.isPresent()) {
			throw new InvalidException(CodeExceptions.CARRIER_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.CARRIER_EXISTS));
		}
		
		if(!optionalCarrier.get().getManufacterPartNumber().equals(request.getManufacterPartNumber())) {
			Optional<Carrier> optionalCarrierByManufacter = carrierRepository.findByManufacterPartNumber(request.getManufacterPartNumber());
			
			if(optionalCarrierByManufacter.isPresent()) {
				throw new InvalidException(CodeExceptions.CARRIER_EXISTS,
						CodeExceptions.ERRORS.get(CodeExceptions.CARRIER_EXISTS));
			}
		}
		
		Carrier carrier = optionalCarrier.get();
		carrier.setDescription(request.getDescription());
		carrier.setManufacterPartNumber(request.getManufacterPartNumber());
		carrier.setEncapsulated(request.getEncapsulated());
		carrier.setW(request.getW());
		carrier.setA0(request.getA0());
		carrier.setB0(request.getB0());
		carrier.setK0(request.getK0());
		carrier.setP(request.getP());
		carrier.setT(request.getT());
		carrier.setMetersByReel(request.getMetersByReel());
		carrier.setPocketByReel(request.getPocketByReel());
		carrier.setPriceByReel(request.getPriceByReel());
		carrier.setCostByReel(request.getCostByReel());
		carrier.setCostByMeter(request.getCostByMeter());
		carrier.setProfit(request.getProfit());
		
		if(request.getIdFile() != null) {
			Optional<File> optionalDrawing = fileRepository.findById(request.getIdFile());
			carrier.setDrawing(optionalDrawing.get());
		}
		
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(request.getIdManufacter());
		carrier.setManufacter(optionalManufacter.get());
		
		carrierRepository.save(carrier);
		
		UpdateCarrierResponse response = new UpdateCarrierResponse(carrier);

		return response;
	}
}
