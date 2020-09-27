package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseDirectTransfer;

@Repository
public interface WarehouseDirectTransferRepository extends JpaRepository<WarehouseDirectTransfer, Long> {
}
