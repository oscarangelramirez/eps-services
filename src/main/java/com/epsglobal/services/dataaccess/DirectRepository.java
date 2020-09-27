package com.epsglobal.services.dataaccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Direct;

@Repository
public interface DirectRepository extends JpaRepository<Direct, Long> {
	Optional<Direct> findByManufacterPartNumber(String manufacterPartNumber);
}
