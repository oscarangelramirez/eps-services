package com.epsglobal.services.dataaccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Backup;

@Repository
public interface BackupRepository extends JpaRepository<Backup, Long> {
	Optional<Backup> findByName(String name);

}
