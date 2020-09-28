package com.epsglobal.services.dataaccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Adapter;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseAdapter;

@Repository
public interface WarehouseAdapterRepository extends JpaRepository<WarehouseAdapter, Long> {
	List<WarehouseAdapter> findByWarehouse(Warehouse warehouse);

	Optional<WarehouseAdapter> findByWarehouseAndAdapter(Warehouse warehouse, Adapter adapter);
}
