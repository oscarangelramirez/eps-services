package com.epsglobal.services.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "warehouses_directs")
public class WarehouseDirect {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;
	
	@ManyToOne
    @JoinColumn(name = "direct_id")
	private Direct direct;
	
	@OneToMany(mappedBy = "warehouseDirect")
	private List<WarehouseDirectInput> warehouseDirectInputs;
	
	@OneToMany(mappedBy = "warehouseDirect")
	private List<WarehouseDirectOutput> warehouseDirectOutputs;
	
	@ManyToMany(mappedBy = "warehouseDirects")
	private List<WarehouseDirectTransfer> warehouseDirectTransfers;
	
	@Column(nullable = false)
	private Integer minimium;
	
	@Column(nullable = false)
	private Integer maximium;
	
	@Column(nullable = true)
	private Long onHand;
}