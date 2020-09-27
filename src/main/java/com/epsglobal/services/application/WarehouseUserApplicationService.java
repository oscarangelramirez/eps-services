package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.UserRepository;
import com.epsglobal.services.dataaccess.WarehouseRepository;
import com.epsglobal.services.dataaccess.WarehouseUserRepository;
import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.datatransfer.warehouse.user.AddWarehouseUserRequest;
import com.epsglobal.services.datatransfer.warehouse.user.AddWarehouseUserResponse;
import com.epsglobal.services.datatransfer.warehouse.user.AddWarehouseUsersRequest;
import com.epsglobal.services.datatransfer.warehouse.user.AddWarehouseUsersResponse;
import com.epsglobal.services.datatransfer.warehouse.user.DeleteWarehouseUserRequest;
import com.epsglobal.services.datatransfer.warehouse.user.DeleteWarehouseUserResponse;
import com.epsglobal.services.datatransfer.warehouse.user.DeleteWarehouseUsersRequest;
import com.epsglobal.services.datatransfer.warehouse.user.DeleteWarehouseUsersResponse;
import com.epsglobal.services.datatransfer.warehouse.user.GetWarehouseUserResponse;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseUser;

@Service
public class WarehouseUserApplicationService {	
	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WarehouseUserRepository warehouseUserRepository;
	
	public List<GetWarehouseUserResponse> findAll(Long idWarehouse) throws NotFoundException {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();

		return warehouse.getWarehouseUsers()
				.stream()
				.sorted(Comparator.comparing(WarehouseUser::getId))
				.map(warehouseUsers -> new GetWarehouseUserResponse(warehouseUsers))
				.collect(Collectors.toList());
	}
	
	public AddWarehouseUsersResponse add(Long idWarehouse, AddWarehouseUsersRequest request) throws NotFoundException {
		AddWarehouseUsersResponse response = new AddWarehouseUsersResponse();
		
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		Warehouse warehouse = optionalWarehouse.get();
		
		for (AddWarehouseUserRequest warehouseUserRequest : request.getWarehouseUsers()) {
			Optional<User> optionalUser = userRepository.findById(warehouseUserRequest.getIdUser());
			
			if(optionalUser.isPresent()) {
				WarehouseUser warehouseUser = new WarehouseUser();
				warehouseUser.setWarehouse(warehouse);
				warehouseUser.setUser(optionalUser.get());

				warehouseUserRepository.save(warehouseUser);
				
				AddWarehouseUserResponse warehouseUserResponse = new AddWarehouseUserResponse(warehouseUser);
				
				response.getWarehouseUsers().add(warehouseUserResponse);
			}
		}

		return response;
	}
	
	public DeleteWarehouseUsersResponse delete(Long idWarehouse, DeleteWarehouseUsersRequest request) throws NotFoundException {
		DeleteWarehouseUsersResponse response = new DeleteWarehouseUsersResponse();
		
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		for (DeleteWarehouseUserRequest warehouseUserRequest : request.getWarehouseUsers()) {
			Optional<WarehouseUser> optionalWarehouseUser = warehouseUserRepository.findById(warehouseUserRequest.getId());
			
			if (optionalWarehouseUser.isPresent()) {
				WarehouseUser warehouseUser = optionalWarehouseUser.get();
				
				warehouseUserRepository.delete(warehouseUser);

				DeleteWarehouseUserResponse warehouseUserResponse = new DeleteWarehouseUserResponse(warehouseUser);
				
				response.getWarehouseUsers().add(warehouseUserResponse);
			}
		}
		
		return response;
	}
	
	public List<GetUserResponse> findAllNotInWarehouse(Long idWarehouse) throws NotFoundException {
		Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(idWarehouse);
		
		if (!optionalWarehouse.isPresent()) {
			throw new NotFoundException(CodeExceptions.WAREHOUSE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.WAREHOUSE_NOT_FOUND));
		}
		
		List<User> users = userRepository.findAll();
		
		Warehouse warehouse = optionalWarehouse.get();
		List<WarehouseUser> warehouseUsers = warehouseUserRepository.findByWarehouse(warehouse);
		
		var usersNotInWarehouse = users
				.stream()
				.filter(u -> !warehouseUsers.stream().anyMatch(w -> w.getUser().getId().equals(u.getId())));

		return usersNotInWarehouse
				.sorted(Comparator.comparing(User::getId))
				.map(user -> new GetUserResponse(user))
				.collect(Collectors.toList());
	}
}
