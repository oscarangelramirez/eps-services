package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseAdapterTransferPlace;

@Repository
public interface WarehouseAdapterTransferPlaceRepository extends JpaRepository<WarehouseAdapterTransferPlace, Long> {
}
