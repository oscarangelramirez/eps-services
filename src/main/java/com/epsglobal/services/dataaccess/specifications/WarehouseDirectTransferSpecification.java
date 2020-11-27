package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Direct_;
import com.epsglobal.services.domain.WarehouseDirectTransfer;
import com.epsglobal.services.domain.WarehouseDirectTransferPlace_;
import com.epsglobal.services.domain.WarehouseDirectTransfer_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseDirectTransferSpecification {
	public static Specification<WarehouseDirectTransfer> getByWarehouseOrigin(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDirectTransfer_.origin).get(WarehouseDirectTransferPlace_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDirectTransfer> getByWarehouseDestination(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDirectTransfer_.destination).get(WarehouseDirectTransferPlace_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDirectTransfer> getByDateOrigin(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseDirectTransfer_.origin).get(WarehouseDirectTransferPlace_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDirectTransfer> getByDateDestination(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseDirectTransfer_.destination).get(WarehouseDirectTransferPlace_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDirectTransfer> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDirectTransfer_.origin).get(WarehouseDirectTransferPlace_.direct).get(Direct_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
