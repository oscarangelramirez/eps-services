package com.epsglobal.services.datatransfer.backup;

import com.epsglobal.services.domain.Backup;
import com.fasterxml.jackson.annotation.JsonView;

public class AddBackupResponse {
	@JsonView
	private Long id;

	public AddBackupResponse() {
	}

	public AddBackupResponse(Backup backup) {
		id = backup.getId();
	}
}