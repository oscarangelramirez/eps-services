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
import com.epsglobal.services.dataaccess.PermissionRepository;
import com.epsglobal.services.datatransfer.permission.AddPermissionRequest;
import com.epsglobal.services.datatransfer.permission.AddPermissionResponse;
import com.epsglobal.services.datatransfer.permission.GetPermissionResponse;
import com.epsglobal.services.datatransfer.permission.UpdatePermissionRequest;
import com.epsglobal.services.datatransfer.permission.UpdatePermissionResponse;
import com.epsglobal.services.domain.Permission;

import lombok.NonNull;

@Service
public class PermissionApplicationService {	
	@Autowired
	private PermissionRepository permissionRepository;
	
	public List<GetPermissionResponse> findAll() {
		List<Permission> permissions = permissionRepository.findAll();

		return permissions
				.stream()
				.sorted(Comparator.comparing(Permission::getId))
				.map(user -> new GetPermissionResponse(user))
				.collect(Collectors.toList());
	}
	
	public GetPermissionResponse findById(Long id) throws NotFoundException {
		Optional<Permission> optionalPermission = permissionRepository.findById(id);
		
		if (!optionalPermission.isPresent()) {
			throw new NotFoundException(CodeExceptions.PERMISSION_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.PERMISSION_NOT_FOUND));
		}
		
		return new GetPermissionResponse(optionalPermission.get());
	}
	
	public AddPermissionResponse add(AddPermissionRequest request) throws InvalidException {
		Optional<Permission> optionalPermissionByName = permissionRepository.findByName(request.getName());
		
		if (optionalPermissionByName.isPresent()) {
			throw new InvalidException(CodeExceptions.PERMISSION_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.PERMISSION_EXISTS));
		}
		
		Permission permission = new Permission();
		permission.setName(request.getName());
		permission.setDescription(request.getDescription());
		permission.setModule(request.getModule());
		permission.setScreen(request.getScreen());

		permissionRepository.save(permission);

		AddPermissionResponse response = new AddPermissionResponse(permission);

		return response;
	}
	
	public UpdatePermissionResponse update(long id, @NonNull UpdatePermissionRequest request) throws NotFoundException, InvalidException  {
		Optional<Permission> optionalPermission = permissionRepository.findById(id);
		
		if (!optionalPermission.isPresent()) {
			throw new NotFoundException(CodeExceptions.PERMISSION_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.PERMISSION_NOT_FOUND));
		}
		
		if(!optionalPermission.get().getName().equals(request.getName())) {
			Optional<Permission> optionalPermissionByName = permissionRepository.findByName(request.getName());
			
			if (optionalPermissionByName.isPresent()) {
				throw new InvalidException(CodeExceptions.PERMISSION_EXISTS,
						CodeExceptions.ERRORS.get(CodeExceptions.PERMISSION_EXISTS));
			}
		}
		
		Permission permission = optionalPermission.get();
		permission.setName(request.getName());
		permission.setDescription(request.getDescription());
		permission.setModule(request.getModule());
		permission.setScreen(request.getScreen());
		
		permissionRepository.save(permission);
		
		UpdatePermissionResponse response = new UpdatePermissionResponse(permission);

		return response;
	}
}
