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
@Table(name = "warehouses_adapters")
public class WarehouseAdapter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@ManyToOne
	@JoinColumn(name = "adapter_id")
	private Adapter adapter;

	@OneToMany(mappedBy = "warehouseAdapter")
	private List<WarehouseAdapterInput> warehouseAdapterInputs;

	@OneToMany(mappedBy = "warehouseAdapter")
	private List<WarehouseAdapterOutput> warehouseAdapterOutputs;

	@ManyToMany(mappedBy = "warehouseAdapters")
	private List<WarehouseAdapterTransfer> warehouseAdapterTransfers;

	@Column(nullable = true)
	private Long onHand;
}
