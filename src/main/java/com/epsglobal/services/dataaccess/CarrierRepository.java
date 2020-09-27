package com.epsglobal.services.dataaccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Carrier;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {
	Optional<Carrier> findByManufacterPartNumber(String manufacterPartNumber);
}
