package com.epsglobal.services.application;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsglobal.services.application.exceptions.CodeExceptions;
import com.epsglobal.services.application.exceptions.InvalidException;
import com.epsglobal.services.application.exceptions.NotFoundException;
import com.epsglobal.services.application.properties.DatabaseProperties;
import com.epsglobal.services.dataaccess.DatabaseRepository;
import com.epsglobal.services.datatransfer.database.AddBackupResponse;
import com.epsglobal.services.datatransfer.database.GetBackupResponse;
import com.epsglobal.services.domain.Backup;

@Service
public class DatabaseApplicationService {
	
	@Autowired
	private DatabaseRepository databaseRepository;

	@Autowired
	private DatabaseProperties databaseProperties;
	
	public AddBackupResponse createBackup() throws InvalidException, IOException, InterruptedException {

		System.out.println("Backup Started at " + new Date());

		Date backupDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		String backupDateStr = format.format(backupDate);

		File folderBackup = new File(databaseProperties.getBackupPath());
		folderBackup.mkdir();

		StringBuilder saveFileName = new StringBuilder();
		saveFileName.append(databaseProperties.getFileNameBackup());
		saveFileName.append("_");
		saveFileName.append(backupDateStr);
		saveFileName.append(databaseProperties.getFileType());

		StringBuilder savePath = new StringBuilder();
		savePath.append(databaseProperties.getBackupPath());
		savePath.append(File.separator);
		savePath.append(saveFileName.toString());

		StringBuilder executeCommand = new StringBuilder();
		executeCommand.append(databaseProperties.getPath());
		executeCommand.append(File.separator);
		executeCommand.append(databaseProperties.getCommandBackup());
		executeCommand.append(" -u ");
		executeCommand.append(databaseProperties.getUsername());
		executeCommand.append(" -p");
		executeCommand.append(databaseProperties.getPassword());
		executeCommand.append(" --databases ");
		executeCommand.append(databaseProperties.getToBackup());
		executeCommand.append(" -r ");
		executeCommand.append(savePath.toString());

		System.out.println("command: " + executeCommand.toString().toString());

		Process runtimeProcess = Runtime.getRuntime().exec(executeCommand.toString());
		int processComplete = runtimeProcess.waitFor();

		if (processComplete == 0) {

			Optional<Backup> optionalBackup = databaseRepository.findByName(saveFileName.toString());
			if (optionalBackup.isPresent()) {
				throw new InvalidException(CodeExceptions.BACKUP_EXISTS,
						CodeExceptions.ERRORS.get(CodeExceptions.BACKUP_EXISTS));
			}

			Date creationBackupDate = new Date();
			System.out.println("Backup Complete at " + creationBackupDate);

			Backup backup = new Backup();
			backup.setDate(creationBackupDate);
			backup.setName(saveFileName.toString());
			backup.setPath(databaseProperties.getBackupPath());

			databaseRepository.save(backup);
			AddBackupResponse response = new AddBackupResponse(backup);
			return response;

		} else {
			System.out.println("Backup Failure");
			return new AddBackupResponse();
		}
	}
	
	public List<GetBackupResponse> findAll() {
		List<Backup> backups = databaseRepository.findAll();

		return backups
				.stream()
				.sorted(Comparator.comparing(Backup::getId))
				.map(backup -> new GetBackupResponse(backup))
				.collect(Collectors.toList());
	}
	
	public GetBackupResponse findById(Long id) throws NotFoundException {
		Optional<Backup> optionalBackup = databaseRepository.findById(id);

		if (!optionalBackup.isPresent()) {
			throw new NotFoundException(CodeExceptions.BACKUP_NOT_FOUND,
					CodeExceptions.ERRORS.get(CodeExceptions.BACKUP_NOT_FOUND));
		}

		return new GetBackupResponse(optionalBackup.get());
	}
}