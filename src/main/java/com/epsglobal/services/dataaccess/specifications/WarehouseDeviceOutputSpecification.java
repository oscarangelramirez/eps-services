package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Device_;
import com.epsglobal.services.domain.WarehouseDeviceOutput;
import com.epsglobal.services.domain.WarehouseDeviceOutput_;
import com.epsglobal.services.domain.WarehouseDevice_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseDeviceOutputSpecification {
	public static Specification<WarehouseDeviceOutput> getByWarehouse(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDeviceOutput_.warehouseDevice).get(WarehouseDevice_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDeviceOutput> getByDate(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseDeviceOutput_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDeviceOutput> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDeviceOutput_.warehouseDevice).get(WarehouseDevice_.device).get(Device_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
