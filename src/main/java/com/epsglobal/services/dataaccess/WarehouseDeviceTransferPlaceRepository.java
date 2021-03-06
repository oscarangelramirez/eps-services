package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseDeviceTransferPlace;

@Repository
public interface WarehouseDeviceTransferPlaceRepository extends JpaRepository<WarehouseDeviceTransferPlace, Long> {
}
