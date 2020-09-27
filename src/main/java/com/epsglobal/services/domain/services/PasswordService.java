package com.epsglobal.services.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.properties.PasswordProperties;

@Service
public class PasswordService {
	@Autowired
    private PasswordProperties passwordProperties;
	
	public void validate(String password) throws InvalidException {
		int digits = 0;
		int upperCase = 0;
		int lowerCase = 0;
		int specials = 0;
		String specialCharacters = "/*!@#$%^&*()\"{}_[]|\\?<>,.";

		if (password.length() < passwordProperties.getMinimumPasswordLength()) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "The password does not meet the minimum length");
		}

		char element;
		for (int index = 0; index < password.length(); index++) {

			element = password.charAt(index);

			if (Character.isDigit(element)) {
				digits++;
			} else if (Character.isUpperCase(element)) {
				upperCase++;
			} else if (Character.isLowerCase(element)) {
				lowerCase++;
			} else if (specialCharacters.contains(password.substring(index, index + 1))) {
				specials++;
			}
		}

		if (digits < passwordProperties.getMinimumPasswordDigits()) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "The password does not meet the minimum digits");
		}

		if (upperCase < passwordProperties.getMinimumPasswordUpperCase()) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "Password does not meet minimum uppercase");
		}

		if (lowerCase < passwordProperties.getMinimumPasswordLowerCase()) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "Password does not meet minimum lowercase");
		}

		if (specials < passwordProperties.getMinimumPasswordSpecials()) {
			throw new InvalidException(CodeExceptions.PASSWORD_INVALID, "The password does not meet the minimum special characters");
		}
	}
}
