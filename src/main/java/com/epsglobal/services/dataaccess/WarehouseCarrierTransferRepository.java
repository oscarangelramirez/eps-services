package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseCarrierTransfer;

@Repository
public interface WarehouseCarrierTransferRepository extends JpaRepository<WarehouseCarrierTransfer, Long> {
}