package com.epsglobal.services.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "warehouses")
public class Warehouse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@OneToOne
	private Location location;
	
	@OneToMany(mappedBy = "warehouse")
    private List<WarehouseUser> warehouseUsers;
	
	@OneToMany(mappedBy = "warehouse")
	private List<WarehouseCarrier> warehouseCarriers;
}
