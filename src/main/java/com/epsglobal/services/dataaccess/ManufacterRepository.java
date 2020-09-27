package com.epsglobal.services.dataaccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Manufacter;

@Repository
public interface ManufacterRepository extends JpaRepository<Manufacter, Long> {
	Optional<Manufacter> findByName(String name);
}
