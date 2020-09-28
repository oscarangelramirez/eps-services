package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseDeviceOutput;

@Repository
public interface WarehouseDeviceOutputRepository extends JpaRepository<WarehouseDeviceOutput, Long> {
}
