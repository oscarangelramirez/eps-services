package com.epsglobal.services.application;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.dataaccess.UserRepository;
import com.epsglobal.services.datatransfer.user.AddUserRequest;
import com.epsglobal.services.datatransfer.user.AddUserResponse;
import com.epsglobal.services.datatransfer.user.GetUserResponse;
import com.epsglobal.services.datatransfer.user.UpdateUserRequest;
import com.epsglobal.services.datatransfer.user.UpdateUserResponse;
import com.epsglobal.services.datatransfer.warehouse.GetWarehouseResponse;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.domain.WarehouseUser;
import com.epsglobal.services.domain.services.PasswordService;
import com.epsglobal.services.infrastructure.CryptographyService;

import lombok.NonNull;

@Service
public class UserApplicationService {
	@Autowired
	private CryptographyService cryptographyService;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<GetUserResponse> findAll() {
		List<User> users = userRepository.findAll();

		return users
				.stream()
				.sorted(Comparator.comparing(User::getId))
				.map(user -> new GetUserResponse(user))
				.collect(Collectors.toList());
	}
	
	public GetUserResponse findById(Long id) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		return new GetUserResponse(optionalUser.get());
	}
	
	public AddUserResponse add(AddUserRequest request) throws InvalidException, ErrorException {
		Optional<User> optionalUserByEmail = userRepository.findByEmail(request.getEmail());
		
		if (optionalUserByEmail.isPresent()) {
			throw new InvalidException(CodeExceptions.USER_EXISTS,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_EXISTS));
		}
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setNumberAttemps(0);
		user.setLastChangeDate(new Date());
		user.setIsLocked(false);
		user.setIsActive(request.getIsActive());

		passwordService.validate(request.getPassword());

		String password = cryptographyService.getSHA256(request.getPassword());

		user.setPassword(password);

		userRepository.save(user);

		AddUserResponse response = new AddUserResponse(user);

		return response;
	}
	
	public UpdateUserResponse update(long id, @NonNull UpdateUserRequest request) throws NotFoundException, InvalidException {
		Optional<User> optionalUser = userRepository.findById(id);
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		if(!optionalUser.get().getEmail().equals(request.getEmail())) {
			Optional<User> optionalUserByEmail = userRepository.findByEmail(request.getEmail());
			
			if (optionalUserByEmail.isPresent()) {
				throw new InvalidException(CodeExceptions.USER_EXISTS,
						CodeExceptions.ERRORS.get(CodeExceptions.USER_EXISTS));
			}
		}
		
		User user = optionalUser.get();
		user.setEmail(request.getEmail());
		user.setIsLocked(request.getIsLocked());
		user.setIsActive(request.getIsActive());
		
		userRepository.save(user);
		
		UpdateUserResponse response = new UpdateUserResponse(user);

		return response;
	}
	
	public List<GetWarehouseResponse> findAllWarehousesByUser(Long id) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		
		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		}
		
		User user = optionalUser.get();
		
		return user
				.getWarehouseUsers()
				.stream()
				.sorted(Comparator.comparing(WarehouseUser::getId))
				.map(warehouseUser -> new GetWarehouseResponse(warehouseUser.getWarehouse()))
				.collect(Collectors.toList());
	}
}
