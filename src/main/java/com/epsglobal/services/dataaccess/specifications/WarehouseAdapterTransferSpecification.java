package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Adapter_;
import com.epsglobal.services.domain.WarehouseAdapterTransfer;
import com.epsglobal.services.domain.WarehouseAdapterTransferPlace_;
import com.epsglobal.services.domain.WarehouseAdapterTransfer_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseAdapterTransferSpecification {
	public static Specification<WarehouseAdapterTransfer> getByWarehouseOrigin(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseAdapterTransfer_.origin).get(WarehouseAdapterTransferPlace_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseAdapterTransfer> getByWarehouseDestination(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseAdapterTransfer_.destination).get(WarehouseAdapterTransferPlace_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseAdapterTransfer> getByDateOrigin(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseAdapterTransfer_.origin).get(WarehouseAdapterTransferPlace_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseAdapterTransfer> getByDateDestination(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseAdapterTransfer_.destination).get(WarehouseAdapterTransferPlace_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseAdapterTransfer> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseAdapterTransfer_.origin).get(WarehouseAdapterTransferPlace_.adapter).get(Adapter_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
