package com.epsglobal.services.datatransfer.backup;

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
	
	@JsonView
	private String comments;

	public GetBackupResponse(Backup backup) {
		id = backup.getId();
		name = backup.getName();
		date = backup.getDate();
		path = backup.getPath();
		comments = backup.getComments();
	}
}
