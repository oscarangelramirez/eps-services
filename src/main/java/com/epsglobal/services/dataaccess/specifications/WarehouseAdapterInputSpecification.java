package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Adapter_;
import com.epsglobal.services.domain.WarehouseAdapterInput;
import com.epsglobal.services.domain.WarehouseAdapterInput_;
import com.epsglobal.services.domain.WarehouseAdapter_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseAdapterInputSpecification {
	public static Specification<WarehouseAdapterInput> getByWarehouse(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseAdapterInput_.warehouseAdapter).get(WarehouseAdapter_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseAdapterInput> getByDate(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseAdapterInput_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseAdapterInput> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseAdapterInput_.warehouseAdapter).get(WarehouseAdapter_.adapter).get(Adapter_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
