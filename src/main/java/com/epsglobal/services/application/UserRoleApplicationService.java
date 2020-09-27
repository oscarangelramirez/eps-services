package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.RoleRepository;
import com.epsglobal.services.dataaccess.UserRepository;
import com.epsglobal.services.dataaccess.UserRoleRepository;
import com.epsglobal.services.datatransfer.role.GetRoleResponse;
import com.epsglobal.services.datatransfer.user.role.AddUserRoleRequest;
import com.epsglobal.services.datatransfer.user.role.AddUserRoleResponse;
import com.epsglobal.services.datatransfer.user.role.AddUserRolesRequest;
import com.epsglobal.services.datatransfer.user.role.AddUserRolesResponse;
import com.epsglobal.services.datatransfer.user.role.DeleteUserRoleRequest;
import com.epsglobal.services.datatransfer.user.role.DeleteUserRoleResponse;
import com.epsglobal.services.datatransfer.user.role.DeleteUserRolesRequest;
import com.epsglobal.services.datatransfer.user.role.DeleteUserRolesResponse;
import com.epsglobal.services.datatransfer.user.role.GetUserRoleResponse;
import com.epsglobal.services.domain.Role;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.UserRole;

@Service
public class UserRoleApplicationService {	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	public List<GetUserRoleResponse> findAll(Long idUser) {
		Optional<User> optionalUser = userRepository.findById(idUser);
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();

		return user.getUserRoles()
				.stream()
				.sorted(Comparator.comparing(UserRole::getId))
				.map(userRoles -> new GetUserRoleResponse(userRoles))
				.collect(Collectors.toList());
	}
	
	public AddUserRolesResponse add(Long idUser, AddUserRolesRequest request) throws NotFoundException {
		AddUserRolesResponse response = new AddUserRolesResponse();
		
		Optional<User> optionalUser = userRepository.findById(idUser);
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();
		
		for (AddUserRoleRequest userRoleRequest : request.getUserRoles()) {
			Optional<Role> optionalRole = roleRepository.findById(userRoleRequest.getIdRole());
			
			if(optionalRole.isPresent()) {
				UserRole userRole = new UserRole();
				userRole.setUser(user);
				userRole.setRole(optionalRole.get());

				userRoleRepository.save(userRole);
				
				AddUserRoleResponse userRoleResponse = new AddUserRoleResponse(userRole);
				
				response.getUserRoles().add(userRoleResponse);
			}
		}

		return response;
	}
	
	public DeleteUserRolesResponse delete(Long idUser, DeleteUserRolesRequest request) throws NotFoundException {
		DeleteUserRolesResponse response = new DeleteUserRolesResponse();
		
		Optional<User> optionalUser = userRepository.findById(idUser);
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();
		
		for (DeleteUserRoleRequest userRoleRequest : request.getUserRoles()) {
			Optional<UserRole> optionalUserRole = userRoleRepository.findById(userRoleRequest.getId());
			
			if (optionalUserRole.isPresent()) {
				UserRole userRole = optionalUserRole.get();
				
				user.getUserRoles().remove(userRole);
				
				userRepository.save(user);
				userRoleRepository.delete(userRole);

				DeleteUserRoleResponse userRoleResponse = new DeleteUserRoleResponse(userRole);
				
				response.getUserRoles().add(userRoleResponse);
			}
		}

		return response;
	}
	
	public List<GetRoleResponse> findAllNotInUser(Long idUser) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findById(idUser);
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		List<Role> roles = roleRepository.findAll();
		
		User user = optionalUser.get();
		List<UserRole> userRoles = userRoleRepository.findByUser(user);
		
		var rolesNotInUser = roles.stream()
		.filter(p -> !userRoles.stream().anyMatch(r -> r.getRole().getId().equals(p.getId())));

		return rolesNotInUser
				.sorted(Comparator.comparing(Role::getId))
				.map(role -> new GetRoleResponse(role))
				.collect(Collectors.toList());
	}
}
