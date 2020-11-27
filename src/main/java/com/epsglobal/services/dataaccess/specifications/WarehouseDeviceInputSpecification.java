package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Device_;
import com.epsglobal.services.domain.WarehouseDeviceInput;
import com.epsglobal.services.domain.WarehouseDeviceInput_;
import com.epsglobal.services.domain.WarehouseDevice_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseDeviceInputSpecification {
	public static Specification<WarehouseDeviceInput> getByWarehouse(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDeviceInput_.warehouseDevice).get(WarehouseDevice_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDeviceInput> getByDate(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseDeviceInput_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDeviceInput> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDeviceInput_.warehouseDevice).get(WarehouseDevice_.device).get(Device_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
