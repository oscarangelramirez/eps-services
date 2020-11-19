package com.epsglobal.services.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.ErrorException;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.application.properties.DatabaseProperties;
import com.epsglobal.services.dataaccess.BackupRepository;
import com.epsglobal.services.datatransfer.backup.AddBackupRequest;
import com.epsglobal.services.datatransfer.backup.AddBackupResponse;
import com.epsglobal.services.datatransfer.backup.DownloadBackupResponse;
import com.epsglobal.services.datatransfer.backup.GetBackupResponse;
import com.epsglobal.services.domain.Backup;

@Service
public class BackupApplicationService {
	
	@Autowired
	private BackupRepository backupRepository;

	private DatabaseProperties databaseProperties;
	
	private final Path fileStorageLocation;
	
	@Autowired
	public BackupApplicationService(DatabaseProperties databaseProperties) {
		this.databaseProperties = databaseProperties;
		fileStorageLocation = Paths.get(databaseProperties.getBackupPath()).toAbsolutePath().normalize();
	}
	
	public List<GetBackupResponse> findAll() {
		List<Backup> backups = backupRepository.findAll();

		return backups
				.stream()
				.sorted(Comparator.comparing(Backup::getId))
				.map(backup -> new GetBackupResponse(backup))
				.collect(Collectors.toList());
	}
	
	public GetBackupResponse findById(Long id) throws NotFoundException {
		Optional<Backup> optionalBackup = backupRepository.findById(id);

		if (!optionalBackup.isPresent()) {
			throw new NotFoundException(CodeExceptions.BACKUP_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.BACKUP_NOT_FOUND));
		}

		return new GetBackupResponse(optionalBackup.get());
	}
	
	public AddBackupResponse add(AddBackupRequest request) throws ErrorException {
		try {
			Date backupDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
			String backupDateStr = format.format(backupDate);

			File folderBackup = new File(databaseProperties.getBackupPath());
			folderBackup.mkdir();

			StringBuilder fileName = new StringBuilder();
			fileName.append(databaseProperties.getFileNameBackup());
			fileName.append("_");
			fileName.append(backupDateStr);
			fileName.append(databaseProperties.getFileType());
			
			Path path = fileStorageLocation.resolve(fileName.toString());

			StringBuilder executeCommand = new StringBuilder();
			executeCommand.append(databaseProperties.getCommandBackup());
			executeCommand.append(" -u ");
			executeCommand.append(databaseProperties.getUsername());
			executeCommand.append(" -p");
			executeCommand.append(databaseProperties.getPassword());
			executeCommand.append(" --databases ");
			executeCommand.append(databaseProperties.getToBackup());
			executeCommand.append(" -r ");
			executeCommand.append(path.toString());

			Process runtimeProcess = Runtime.getRuntime().exec(executeCommand.toString());
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0) {
				Optional<Backup> optionalBackup = backupRepository.findByName(fileName.toString());
				if (optionalBackup.isPresent()) {
					throw new InvalidException(CodeExceptions.BACKUP_EXISTS,
							CodeExceptions.ERRORS.get(CodeExceptions.BACKUP_EXISTS));
				}

				Date creationBackupDate = new Date();

				Backup backup = new Backup();
				backup.setDate(creationBackupDate);
				backup.setName(fileName.toString());
				backup.setPath(path.toString());
				backup.setComments(request.getComments());

				backupRepository.save(backup);
				AddBackupResponse response = new AddBackupResponse(backup);
				return response;

			} else {
				throw new ErrorException(CodeExceptions.BACKUP_ERROR,
						CodeExceptions.ERRORS.get(CodeExceptions.BACKUP_ERROR));
			}
		}
		catch(Exception exception) {
			exception.printStackTrace();
			throw new ErrorException(CodeExceptions.BACKUP_ERROR,
					CodeExceptions.ERRORS.get(CodeExceptions.BACKUP_ERROR));
		}
	}
	
	public DownloadBackupResponse Download(Long id) throws NotFoundException, ErrorException  {
		Optional<Backup> optionalBackup = backupRepository.findById(id);

		if (!optionalBackup.isPresent()) {
			throw new NotFoundException(CodeExceptions.BACKUP_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.BACKUP_NOT_FOUND));
		}
		
		try {
			Backup backup = optionalBackup.get();
			Path path = Paths.get(backup.getPath());
			byte[] bytes = Files.readAllBytes(path);
			
			DownloadBackupResponse response = new DownloadBackupResponse(backup);
			response.setBytes(bytes);
			
			return response;
		}
		catch(Exception exception) {
			throw new ErrorException(CodeExceptions.ERROR, exception.getMessage());
		}
	}
}