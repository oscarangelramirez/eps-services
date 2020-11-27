package com.epsglobal.services.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.WarehouseDirectOutput;

@Repository
public interface WarehouseDirectOutputRepository extends JpaRepository<WarehouseDirectOutput, Long>, JpaSpecificationExecutor<WarehouseDirectOutput> {
}
