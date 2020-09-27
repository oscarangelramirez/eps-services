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
import com.epsglobal.services.dataaccess.RoleRepository;
import com.epsglobal.services.datatransfer.role.AddRoleRequest;
import com.epsglobal.services.datatransfer.role.AddRoleResponse;
import com.epsglobal.services.datatransfer.role.GetRoleResponse;
import com.epsglobal.services.datatransfer.role.UpdateRoleRequest;
import com.epsglobal.services.datatransfer.role.UpdateRoleResponse;
import com.epsglobal.services.domain.Role;

import lombok.NonNull;

@Service
public class RoleApplicationService {	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<GetRoleResponse> findAll() {
		List<Role> roles = roleRepository.findAll();

		return roles
				.stream()
				.sorted(Comparator.comparing(Role::getId))
				.map(user -> new GetRoleResponse(user))
				.collect(Collectors.toList());
	}
	
	public GetRoleResponse findById(Long id) throws NotFoundException {
		Optional<Role> optionalRole = roleRepository.findById(id);
		
		if (!optionalRole.isPresent()) {
			throw new NotFoundException(CodeExceptions.ROLE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ROLE_NOT_FOUND));
		}
		
		return new GetRoleResponse(optionalRole.get());
	}
	
	public AddRoleResponse add(AddRoleRequest request) throws InvalidException {
		Optional<Role> optionalRoleByName = roleRepository.findByName(request.getName());
		
		if (optionalRoleByName.isPresent()) {
			throw new InvalidException(CodeExceptions.ROLE_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.ROLE_EXISTS));
		}
		
		Role role = new Role();
		role.setName(request.getName());
		role.setDescription(request.getDescription());

		roleRepository.save(role);

		AddRoleResponse response = new AddRoleResponse(role);

		return response;
	}
	
	public UpdateRoleResponse update(long id, @NonNull UpdateRoleRequest request) throws NotFoundException, InvalidException {
		Optional<Role> optionalRole = roleRepository.findById(id);
		
		if (!optionalRole.isPresent()) {
			throw new NotFoundException(CodeExceptions.ROLE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ROLE_NOT_FOUND));
		}
		
		if(!optionalRole.get().getName().equals(request.getName())) {
			Optional<Role> optionalRolByName = roleRepository.findByName(request.getName());
			
			if (optionalRolByName.isPresent()) {
				throw new InvalidException(CodeExceptions.ROLE_EXISTS,
						CodeExceptions.ERRORS.get(CodeExceptions.ROLE_EXISTS));
			}
		}
		
		Role role = optionalRole.get();
		role.setName(request.getName());
		role.setDescription(request.getDescription());
		
		roleRepository.save(role);
		
		UpdateRoleResponse response = new UpdateRoleResponse(role);

		return response;
	}
}
