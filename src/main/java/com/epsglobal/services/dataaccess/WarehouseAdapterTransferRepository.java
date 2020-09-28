package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseAdapterTransfer;

@Repository
public interface WarehouseAdapterTransferRepository extends JpaRepository<WarehouseAdapterTransfer, Long> {
}
