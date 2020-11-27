package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Direct_;
import com.epsglobal.services.domain.WarehouseDirectInput;
import com.epsglobal.services.domain.WarehouseDirectInput_;
import com.epsglobal.services.domain.WarehouseDirect_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseDirectInputSpecification {
	public static Specification<WarehouseDirectInput> getByWarehouse(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDirectInput_.warehouseDirect).get(WarehouseDirect_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDirectInput> getByDate(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseDirectInput_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDirectInput> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDirectInput_.warehouseDirect).get(WarehouseDirect_.direct).get(Direct_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
