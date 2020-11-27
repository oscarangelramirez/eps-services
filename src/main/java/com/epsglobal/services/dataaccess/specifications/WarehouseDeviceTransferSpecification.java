package com.epsglobal.services.dataaccess.specifications;

import java.util.Date;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.epsglobal.services.domain.Device_;
import com.epsglobal.services.domain.WarehouseDeviceTransfer;
import com.epsglobal.services.domain.WarehouseDeviceTransferPlace_;
import com.epsglobal.services.domain.WarehouseDeviceTransfer_;
import com.epsglobal.services.domain.Warehouse_;

public class WarehouseDeviceTransferSpecification {
	public static Specification<WarehouseDeviceTransfer> getByWarehouseOrigin(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDeviceTransfer_.origin).get(WarehouseDeviceTransferPlace_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDeviceTransfer> getByWarehouseDestination(Long idWarehouse) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDeviceTransfer_.destination).get(WarehouseDeviceTransferPlace_.warehouse).get(Warehouse_.id), idWarehouse);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDeviceTransfer> getByDateOrigin(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseDeviceTransfer_.origin).get(WarehouseDeviceTransferPlace_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDeviceTransfer> getByDateDestination(Date fromDate, Date toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate equalPredicate = criteriaBuilder.between(root.get(WarehouseDeviceTransfer_.destination).get(WarehouseDeviceTransferPlace_.date), fromDate, toDate);
            return equalPredicate;
        };
    }
	
	public static Specification<WarehouseDeviceTransfer> getByManufacterPartNumber(String manufacterPartNumber) {
        return (root, query, criteriaBuilder) -> {
        	Predicate equalPredicate = criteriaBuilder.equal(root.get(WarehouseDeviceTransfer_.origin).get(WarehouseDeviceTransferPlace_.device).get(Device_.manufacterPartNumber), manufacterPartNumber);
            return equalPredicate;
        };
    }
}
