package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseDeviceInput;

@Repository
public interface WarehouseDeviceInputRepository extends JpaRepository<WarehouseDeviceInput, Long>, JpaSpecificationExecutor<WarehouseDeviceInput> {
}
