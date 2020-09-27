package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.PermissionRepository;
import com.epsglobal.services.dataaccess.RolePermissionRepository;
import com.epsglobal.services.dataaccess.RoleRepository;
import com.epsglobal.services.datatransfer.permission.GetPermissionResponse;
import com.epsglobal.services.datatransfer.role.permission.AddRolePermissionRequest;
import com.epsglobal.services.datatransfer.role.permission.AddRolePermissionResponse;
import com.epsglobal.services.datatransfer.role.permission.AddRolePermissionsRequest;
import com.epsglobal.services.datatransfer.role.permission.AddRolePermissionsResponse;
import com.epsglobal.services.datatransfer.role.permission.GetRolePermissionResponse;
import com.epsglobal.services.datatransfer.role.permission.UpdateRolePermissionRequest;
import com.epsglobal.services.datatransfer.role.permission.UpdateRolePermissionResponse;
import com.epsglobal.services.domain.Permission;
import com.epsglobal.services.domain.Role;
import com.epsglobal.services.domain.RolePermission;

@Service
public class RolePermissionApplicationService {	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private RolePermissionRepository rolePermissionRepository;
	
	public List<GetRolePermissionResponse> findAll(Long idRol) {
		Optional<Role> optionalRole = roleRepository.findById(idRol);
		
		if (!optionalRole.isPresent()) {
			throw new NotFoundException(CodeExceptions.ROLE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ROLE_NOT_FOUND));
		}
		
		Role role = optionalRole.get();

		return role.getRolePermissions()
				.stream()
				.sorted(Comparator.comparing(RolePermission::getId))
				.map(rolPermission -> new GetRolePermissionResponse(rolPermission))
				.collect(Collectors.toList());
	}
	
	public AddRolePermissionsResponse add(Long idRol, AddRolePermissionsRequest request) throws NotFoundException {
		AddRolePermissionsResponse response = new AddRolePermissionsResponse();
		
		Optional<Role> optionalRole = roleRepository.findById(idRol);
		
		if (!optionalRole.isPresent()) {
			throw new NotFoundException(CodeExceptions.ROLE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ROLE_NOT_FOUND));
		}
		
		Role role = optionalRole.get();
		
		for (AddRolePermissionRequest rolPermissionRequest : request.getRolePermissions()) {
			Optional<Permission> optionalPermission = permissionRepository.findById(rolPermissionRequest.getIdPermission());
			
			if(optionalPermission.isPresent()) {
				RolePermission rolePermission = new RolePermission();
				rolePermission.setType(RolePermission.Type.values()[rolPermissionRequest.getType()]);
				rolePermission.setRole(role);
				rolePermission.setPermission(optionalPermission.get());

				rolePermissionRepository.save(rolePermission);
				
				AddRolePermissionResponse rolePermissionResponse = new AddRolePermissionResponse(rolePermission);
				
				response.getRolePermissions().add(rolePermissionResponse);
			}
		}

		return response;
	}
	
	public UpdateRolePermissionResponse update(Long idRol, Long id, UpdateRolePermissionRequest request) throws NotFoundException {
		Optional<Role> optionalRole = roleRepository.findById(idRol);
		
		if (!optionalRole.isPresent()) {
			throw new NotFoundException(CodeExceptions.ROLE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ROLE_NOT_FOUND));
		}
		
		Optional<RolePermission> optionalRolePermission = rolePermissionRepository.findById(id);
		
		if (!optionalRolePermission.isPresent()) {
			throw new NotFoundException(CodeExceptions.ROL_PERMISSION_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ROL_PERMISSION_NOT_FOUND));
		}
		
		RolePermission rolePermission = optionalRolePermission.get();
		rolePermission.setType(RolePermission.Type.values()[request.getType()]);
		
		rolePermissionRepository.save(rolePermission);

		UpdateRolePermissionResponse response = new UpdateRolePermissionResponse(rolePermission);

		return response;
	}
	
	public List<GetPermissionResponse> findAllNotInRol(Long idRol) throws NotFoundException {
		Optional<Role> optionalRole = roleRepository.findById(idRol);
		
		if (!optionalRole.isPresent()) {
			throw new NotFoundException(CodeExceptions.ROLE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.ROLE_NOT_FOUND));
		}
		
		List<Permission> permissions = permissionRepository.findAll();
		
		Role role = optionalRole.get();
		List<RolePermission> rolePermissions = rolePermissionRepository.findByRole(role);
		
		var permissionsNotInRol = permissions.stream()
		.filter(p -> !rolePermissions.stream().anyMatch(r -> r.getPermission().getId().equals(p.getId())));

		return permissionsNotInRol
				.sorted(Comparator.comparing(Permission::getId))
				.map(permission -> new GetPermissionResponse(permission))
				.collect(Collectors.toList());
	}
}
