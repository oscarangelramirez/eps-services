package com.epsglobal.services.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "password")
public class PasswordProperties {
	private Integer minimumPasswordLength;
	private Integer minimumPasswordDigits;
	private Integer minimumPasswordUpperCase;
	private Integer minimumPasswordLowerCase;
	private Integer minimumPasswordSpecials;
}
