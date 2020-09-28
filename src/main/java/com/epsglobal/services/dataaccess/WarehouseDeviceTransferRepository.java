package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseDeviceTransfer;

@Repository
public interface WarehouseDeviceTransferRepository extends JpaRepository<WarehouseDeviceTransfer, Long> {
}
