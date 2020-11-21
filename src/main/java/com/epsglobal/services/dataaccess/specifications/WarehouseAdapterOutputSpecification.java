package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Adapter_;
import com.epsglobal.services.domain.WarehouseAdapterOutput;
import com.epsglobal.services.domain.WarehouseAdapterOutput_;
import com.epsglobal.services.domain.WarehouseAdapter_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseAdapterOutputSpecification {
	public static Specification<WarehouseAdapterOutput> getByWarehouse(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseAdapterOutput_.warehouseAdapter).get(WarehouseAdapter_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseAdapterOutput> getByDate(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseAdapterOutput_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseAdapterOutput> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseAdapterOutput_.warehouseAdapter).get(WarehouseAdapter_.adapter).get(Adapter_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
