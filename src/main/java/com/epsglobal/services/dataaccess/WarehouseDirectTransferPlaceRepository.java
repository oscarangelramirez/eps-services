package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseDirectTransferPlace;

@Repository
public interface WarehouseDirectTransferPlaceRepository extends JpaRepository<WarehouseDirectTransferPlace, Long> {
}
