package com.epsglobal.services.dataaccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
	Optional<Device> findByManufacterPartNumber(String manufacterPartNumber);
}
