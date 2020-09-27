package com.epsglobal.services.dataaccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Direct;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseDirect;

@Repository
public interface WarehouseDirectRepository extends JpaRepository<WarehouseDirect, Long> {
	List<WarehouseDirect> findByWarehouse(Warehouse warehouse);
	Optional<WarehouseDirect> findByWarehouseAndDirect(Warehouse warehouse, Direct direct);
}
