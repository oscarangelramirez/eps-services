package com.epsglobal.services.datatransfer.file;

import com.epsglobal.services.domain.File;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class GetFileResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	@JsonView
	private String path;
	
	@JsonIgnore
	private byte[] bytes;
	
	public GetFileResponse(File file) {
		id = file.getId();
		name = file.getName();
		path = file.getPath();
	}
}
