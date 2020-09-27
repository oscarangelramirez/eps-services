package com.epsglobal.services.application;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.application.properties.UserProperties;
import com.epsglobal.services.dataaccess.UserRepository;
import com.epsglobal.services.datatransfer.authentication.LoginUserRequest;
import com.epsglobal.services.datatransfer.authentication.LoginUserResponse;
import com.epsglobal.services.domain.User;
import com.epsglobal.services.infrastructure.CryptographyService;

@Service
public class AuthenticationApplicationService {
	@Autowired
	private CryptographyService cryptographyService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserProperties userProperties;
	
	public LoginUserResponse login(LoginUserRequest request) throws NotFoundException, InvalidException, ErrorException {
		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

		if (!optionalUser.isPresent()) {
			throw new NotFoundException(CodeExceptions.USER_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.USER_NOT_FOUND));
		} else {
			User user = optionalUser.get();

			if (!user.getIsActive()) {
				throw new InvalidException(CodeExceptions.USER_INACTIVE,
						CodeExceptions.ERRORS.get(CodeExceptions.USER_INACTIVE));
			}

			if (user.getIsLocked()) {
				throw new InvalidException(CodeExceptions.USER_BLOCKED,
						CodeExceptions.ERRORS.get(CodeExceptions.USER_BLOCKED));
			}

			String password = cryptographyService.getSHA256(request.getPassword());

			if (!password.equals(user.getPassword())) {
				Integer numberAttemps = user.getNumberAttemps();
				numberAttemps++;

				if (numberAttemps == userProperties.getMaximumPasswordAttemps()) {
					user.setIsLocked(true);
				}

				user.setNumberAttemps(numberAttemps);

				userRepository.save(user);

				throw new InvalidException(CodeExceptions.PASSWORD_INVALID,
						CodeExceptions.ERRORS.get(CodeExceptions.PASSWORD_INVALID));
			} else {
				Date lastChangeDate = user.getLastChangeDate();
				Date today = new Date();

				long diff = today.getTime() - lastChangeDate.getTime();
				long days = diff / (24 * 60 * 60 * 1000);

				if (days >= userProperties.getMaximumPasswordChange()) {
					throw new InvalidException(CodeExceptions.MUST_CHANGE_PASSWORD,
							CodeExceptions.ERRORS.get(CodeExceptions.MUST_CHANGE_PASSWORD));
				}
				
				user.setNumberAttemps(0);

				userRepository.save(user);

				LoginUserResponse response = new LoginUserResponse(user);

				return response;
			}
		}
	}
}
