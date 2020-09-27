package com.epsglobal.services.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epsglobal.services.domain.Warehouse;
import com.epsglobal.services.domain.WarehouseUser;

@Repository
public interface WarehouseUserRepository extends JpaRepository<WarehouseUser, Long> {
	List<WarehouseUser> findByWarehouse(Warehouse warehouse);
}
