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
import com.epsglobal.services.dataaccess.ManufacterRepository;
import com.epsglobal.services.datatransfer.manufacter.AddManufacterRequest;
import com.epsglobal.services.datatransfer.manufacter.AddManufacterResponse;
import com.epsglobal.services.datatransfer.manufacter.GetManufacterResponse;
import com.epsglobal.services.datatransfer.manufacter.UpdateManufacterRequest;
import com.epsglobal.services.datatransfer.manufacter.UpdateManufacterResponse;
import com.epsglobal.services.domain.Manufacter;

import lombok.NonNull;

@Service
public class ManufacterApplicationService {
	@Autowired
    private ManufacterRepository manufacterRepository;
	
	public List<GetManufacterResponse> findAll() {
		List<Manufacter> manufacters = manufacterRepository.findAll();

		return manufacters
				.stream()
				.sorted(Comparator.comparing(Manufacter::getId))
				.map(manufacter -> new GetManufacterResponse(manufacter))
				.collect(Collectors.toList());
	}
	
	public GetManufacterResponse findById(Long id) throws NotFoundException {
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(id);
		
		if (!optionalManufacter.isPresent()) {
			throw new NotFoundException(CodeExceptions.MANUFACTER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.MANUFACTER_NOT_FOUND));
		}
		
		return new GetManufacterResponse(optionalManufacter.get());
	}
	
	public AddManufacterResponse add(@NonNull AddManufacterRequest request) throws InvalidException {
		Optional<Manufacter> optionalManufacterByName = manufacterRepository.findByName(request.getName());
		
		if(optionalManufacterByName.isPresent()) {
			throw new InvalidException(CodeExceptions.MANUFACTER_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.MANUFACTER_EXISTS));
		}
		
		Manufacter manufacter = new Manufacter();
		
		manufacter.setName(request.getName());
		
		manufacterRepository.save(manufacter);
		
		AddManufacterResponse response = new AddManufacterResponse(manufacter);

		return response;
	}
	
	public UpdateManufacterResponse update(long id, @NonNull UpdateManufacterRequest request) throws NotFoundException, InvalidException {
		Optional<Manufacter> optionalManufacter = manufacterRepository.findById(id);
		
		if (!optionalManufacter.isPresent()) {
			throw new NotFoundException(CodeExceptions.MANUFACTER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.MANUFACTER_NOT_FOUND));
		}
		
		Optional<Manufacter> optionalManufacterByName = manufacterRepository.findByName(request.getName());
		
		if(optionalManufacterByName.isPresent()) {
			throw new InvalidException(CodeExceptions.MANUFACTER_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.MANUFACTER_EXISTS));
		}
		
		Manufacter manufacter = optionalManufacter.get();
		manufacter.setName(request.getName());
		
		manufacterRepository.save(manufacter);
		
		UpdateManufacterResponse response = new UpdateManufacterResponse(manufacter);

		return response;
	}
}
