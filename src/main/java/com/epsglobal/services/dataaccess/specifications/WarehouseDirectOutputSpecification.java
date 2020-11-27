package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Direct_;
import com.epsglobal.services.domain.WarehouseDirectOutput;
import com.epsglobal.services.domain.WarehouseDirectOutput_;
import com.epsglobal.services.domain.WarehouseDirect_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseDirectOutputSpecification {
	public static Specification<WarehouseDirectOutput> getByWarehouse(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDirectOutput_.warehouseDirect).get(WarehouseDirect_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDirectOutput> getByDate(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseDirectOutput_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDirectOutput> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDirectOutput_.warehouseDirect).get(WarehouseDirect_.direct).get(Direct_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
