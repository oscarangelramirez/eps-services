package com.epsglobal.services.dataaccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Carrier;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseCarrier;

@Repository
public interface WarehouseCarrierRepository extends JpaRepository<WarehouseCarrier, Long> {
	List<WarehouseCarrier> findByWarehouse(Warehouse warehouse);
	Optional<WarehouseCarrier> findByWarehouseAndCarrier(Warehouse warehouse, Carrier carrier);
}
