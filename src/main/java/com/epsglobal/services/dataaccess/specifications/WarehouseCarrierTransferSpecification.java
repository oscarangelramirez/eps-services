package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Carrier_;
import com.epsglobal.services.domain.WarehouseCarrierTransfer;
import com.epsglobal.services.domain.WarehouseCarrierTransferPlace_;
import com.epsglobal.services.domain.WarehouseCarrierTransfer_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseCarrierTransferSpecification {
	public static Specification<WarehouseCarrierTransfer> getByWarehouseOrigin(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseCarrierTransfer_.origin).get(WarehouseCarrierTransferPlace_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseCarrierTransfer> getByWarehouseDestination(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseCarrierTransfer_.destination).get(WarehouseCarrierTransferPlace_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseCarrierTransfer> getByDateOrigin(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseCarrierTransfer_.origin).get(WarehouseCarrierTransferPlace_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseCarrierTransfer> getByDateDestination(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseCarrierTransfer_.destination).get(WarehouseCarrierTransferPlace_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseCarrierTransfer> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseCarrierTransfer_.origin).get(WarehouseCarrierTransferPlace_.carrier).get(Carrier_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
