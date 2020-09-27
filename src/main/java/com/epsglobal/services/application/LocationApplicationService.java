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
import com.epsglobal.services.dataaccess.LocationRepository;
import com.epsglobal.services.datatransfer.location.AddLocationRequest;
import com.epsglobal.services.datatransfer.location.AddLocationResponse;
import com.epsglobal.services.datatransfer.location.GetLocationResponse;
import com.epsglobal.services.datatransfer.location.UpdateLocationRequest;
import com.epsglobal.services.datatransfer.location.UpdateLocationResponse;
import com.epsglobal.services.domain.Location;

import lombok.NonNull;

@Service
public class LocationApplicationService {
	@Autowired
    private LocationRepository locationRepository;
	
	public List<GetLocationResponse> findAll() {
		List<Location> locations = locationRepository.findAll();

		return locations
				.stream()
				.sorted(Comparator.comparing(Location::getId))
				.map(branch -> new GetLocationResponse(branch))
				.collect(Collectors.toList());
	}
	
	public GetLocationResponse findById(Long id) throws NotFoundException {
		Optional<Location> optionalLocation = locationRepository.findById(id);
		
		if (!optionalLocation.isPresent()) {
			throw new NotFoundException(CodeExceptions.LOCATION_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.LOCATION_NOT_FOUND));
		}
		
		return new GetLocationResponse(optionalLocation.get());
	}
	
	public AddLocationResponse add(@NonNull AddLocationRequest request) throws InvalidException {
		Optional<Location> optionalLocationByName = locationRepository.findByName(request.getName());
		
		if(optionalLocationByName.isPresent()) {
			throw new InvalidException(CodeExceptions.LOCATION_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.LOCATION_EXISTS));
		}
		
		Location location = new Location();
		
		location.setName(request.getName());
		
		locationRepository.save(location);
		
		AddLocationResponse response = new AddLocationResponse(location);

		return response;
	}
	
	public UpdateLocationResponse update(long id, @NonNull UpdateLocationRequest request) throws NotFoundException, InvalidException {
		Optional<Location> optionalLocation = locationRepository.findById(id);
		
		if (!optionalLocation.isPresent()) {
			throw new NotFoundException(CodeExceptions.LOCATION_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.LOCATION_NOT_FOUND));
		}
		
		Optional<Location> optionalLocationByName = locationRepository.findByName(request.getName());
		
		if(optionalLocationByName.isPresent()) {
			throw new InvalidException(CodeExceptions.LOCATION_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.LOCATION_EXISTS));
		}
		
		Location location = optionalLocation.get();
		location.setName(request.getName());
		
		locationRepository.save(location);
		
		UpdateLocationResponse response = new UpdateLocationResponse(location);

		return response;
	}
}
