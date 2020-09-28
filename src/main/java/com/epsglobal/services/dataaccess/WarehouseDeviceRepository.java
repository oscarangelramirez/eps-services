package com.epsglobal.services.dataaccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Device;
import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseDevice;

@Repository
public interface WarehouseDeviceRepository extends JpaRepository<WarehouseDevice, Long> {
	List<WarehouseDevice> findByWarehouse(Warehouse warehouse);

	Optional<WarehouseDevice> findByWarehouseAndDevice(Warehouse warehouse, Device device);
}
