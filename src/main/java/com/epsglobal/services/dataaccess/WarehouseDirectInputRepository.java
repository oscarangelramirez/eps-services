package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseDirectInput;

@Repository
public interface WarehouseDirectInputRepository extends JpaRepository<WarehouseDirectInput, Long>, JpaSpecificationExecutor<WarehouseDirectInput> {
}
