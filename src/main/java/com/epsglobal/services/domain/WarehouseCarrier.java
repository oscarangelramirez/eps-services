package com.epsglobal.services.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "warehouses_carriers")
public class WarehouseCarrier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;
	
	@ManyToOne
    @JoinColumn(name = "carrier_id")
	private Carrier carrier;
	
	@OneToMany
	@JoinColumn(name = "warehouse_carrier_id")
	private List<WarehouseCarrierInput> warehouseCarrierInputs;
	
	@OneToMany
	@JoinColumn(name = "warehouse_carrier_id")
	private List<WarehouseCarrierOutput> warehouseCarrierOutputs;
	
	@ManyToMany
	@JoinTable(
	  name = "warehouses_carriers_transfers_relationship", 
	  joinColumns = @JoinColumn(name = "warehouses_carrier_id"), 
	  inverseJoinColumns = @JoinColumn(name = "warehouses_carriers_transfer_id"))
	private List<WarehouseCarrierTransfer> warehouseCarrierTransfers;
	
	@Column(nullable = false)
	private Integer minimium;
	
	@Column(nullable = false)
	private Integer maximium;
	
	@Column(nullable = true)
	private Long onHandComplete;
	
	@Column(nullable = true)
	private Long onHandPartial;
}