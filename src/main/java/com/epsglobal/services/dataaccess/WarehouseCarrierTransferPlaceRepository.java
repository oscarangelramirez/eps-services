package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseCarrierTransferPlace;

@Repository
public interface WarehouseCarrierTransferPlaceRepository extends JpaRepository<WarehouseCarrierTransferPlace, Long> {
}
