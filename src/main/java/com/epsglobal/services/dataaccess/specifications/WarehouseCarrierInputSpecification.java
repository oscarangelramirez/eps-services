package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Carrier_;
import com.epsglobal.services.domain.WarehouseCarrierInput;
import com.epsglobal.services.domain.WarehouseCarrierInput_;
import com.epsglobal.services.domain.WarehouseCarrier_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseCarrierInputSpecification {
	public static Specification<WarehouseCarrierInput> getByWarehouse(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseCarrierInput_.warehouseCarrier).get(WarehouseCarrier_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseCarrierInput> getByDate(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseCarrierInput_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseCarrierInput> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseCarrierInput_.warehouseCarrier).get(WarehouseCarrier_.carrier).get(Carrier_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
