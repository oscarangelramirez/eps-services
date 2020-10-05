package com.epsglobal.services.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {

	private String path;
	private String backupPath;
	private String toBackup;
	private String fileNameBackup;
	private String fileType;
	private String username;
	private String password;
	private String commandBackup;
}
