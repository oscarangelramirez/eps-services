package com.epsglobal.services.dataaccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Adapter;

@Repository
public interface AdapterRepository extends JpaRepository<Adapter, Long> {
	Optional<Adapter> findByManufacterPartNumber(String manufacterPartNumber);
}
