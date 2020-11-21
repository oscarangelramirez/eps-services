package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Carrier_;
import com.epsglobal.services.domain.WarehouseCarrierOutput;
import com.epsglobal.services.domain.WarehouseCarrierOutput_;
import com.epsglobal.services.domain.WarehouseCarrier_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseCarrierOutputSpecification {
	public static Specification<WarehouseCarrierOutput> getByWarehouse(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseCarrierOutput_.warehouseCarrier).get(WarehouseCarrier_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseCarrierOutput> getByDate(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseCarrierOutput_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseCarrierOutput> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseCarrierOutput_.warehouseCarrier).get(WarehouseCarrier_.carrier).get(Carrier_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
