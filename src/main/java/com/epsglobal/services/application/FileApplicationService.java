package com.epsglobal.services.application;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.application.properties.FileStorageProperties;
import com.epsglobal.services.dataaccess.FileRepository;
import com.epsglobal.services.datatransfer.file.AddFileResponse;
import com.epsglobal.services.datatransfer.file.GetFileResponse;
import com.epsglobal.services.datatransfer.file.UpdateFileResponse;
import com.epsglobal.services.domain.File;

@Service
public class FileApplicationService {
	@Autowired
    private FileRepository fileRepository;
	
	private final Path fileStorageLocation;
	
	@Autowired
	public FileApplicationService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDirectory()).toAbsolutePath().normalize();
	}
	
	public GetFileResponse get(Long id) throws NotFoundException, ErrorException  {
		Optional<File> optionalFile = fileRepository.findById(id);
		if (!optionalFile.isPresent()) {
			throw new NotFoundException(CodeExceptions.FILE_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.FILE_NOT_FOUND));
		}
		
		try {
			File file = optionalFile.get();
			Path path = Paths.get(file.getPath());
			byte[] bytes = Files.readAllBytes(path);
			
			GetFileResponse response = new GetFileResponse(file);
			response.setBytes(bytes);
			
			return response;
		}
		catch(Exception exception) {
			throw new ErrorException(CodeExceptions.ERROR, exception.getMessage());
		}
	}
	
	public AddFileResponse add(MultipartFile request) throws ErrorException {
		try {
			String fileName = StringUtils.cleanPath(request.getOriginalFilename());
			Path path = this.fileStorageLocation.resolve(fileName);
			Files.copy(request.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			File file = new File();
			file.setName(fileName);
			file.setPath(path.toString());
			
			fileRepository.save(file);
			
			AddFileResponse response = new AddFileResponse(file);

			return response;
		}
		catch(Exception exception) {
			throw new ErrorException(CodeExceptions.ERROR, exception.getMessage());
		}
	}
	
	public UpdateFileResponse update(long id, MultipartFile request) throws NotFoundException, ErrorException  {
		try {
			Optional<File> optionalFile = fileRepository.findById(id);
			
			if (!optionalFile.isPresent()) {
				throw new NotFoundException(CodeExceptions.FILE_NOT_FOUND,
						CodeExceptions.ERRORS.get(CodeExceptions.FILE_NOT_FOUND));
			}
			
			String fileName = StringUtils.cleanPath(request.getOriginalFilename());
			Path path = this.fileStorageLocation.resolve(fileName);
			Files.copy(request.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			File file = optionalFile.get();
			file.setName(fileName);
			file.setPath(path.toString());
			
			fileRepository.save(file);
			
			UpdateFileResponse response = new UpdateFileResponse(file);

			return response;
		}
		catch(Exception exception) {
			throw new ErrorException(CodeExceptions.ERROR, exception.getMessage());
		}
	}
}
