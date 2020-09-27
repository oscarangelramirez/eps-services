package com.epsglobal.services.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
	private String uploadDirectory;
}
