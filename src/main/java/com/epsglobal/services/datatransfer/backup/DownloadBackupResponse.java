package com.epsglobal.services.datatransfer.backup;

import com.epsglobal.services.domain.Backup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class DownloadBackupResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	@JsonView
	private String path;
	
	@JsonIgnore
	private byte[] bytes;
	
	public DownloadBackupResponse(Backup backup) {
		id = backup.getId();
		name = backup.getName();
		path = backup.getPath();
	}
}
