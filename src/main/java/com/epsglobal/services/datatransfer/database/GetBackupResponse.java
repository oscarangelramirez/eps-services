package com.epsglobal.services.datatransfer.database;

import java.util.Date;

import com.epsglobal.services.domain.Backup;
import com.fasterxml.jackson.annotation.JsonView;

public class GetBackupResponse {

	@JsonView
	private Long id;

	@JsonView
	private String name;

	@JsonView
	private Date date;

	@JsonView
	private String path;

	public GetBackupResponse(Backup backup) {
		this.id = backup.getId();
		this.name = backup.getName();
		this.date = backup.getDate();
		this.path = backup.getPath();
	}
}
