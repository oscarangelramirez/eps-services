package com.epsglobal.services.datatransfer.file;

import com.epsglobal.services.domain.File;
import com.fasterxml.jackson.annotation.JsonView;

public class AddFileResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	@JsonView
	private String path;
	
	public AddFileResponse(File file) {
		id = file.getId();
		name = file.getName();
		path = file.getPath();
	}
}
