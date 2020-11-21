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
@Table(name = "warehouses_devices")
public class WarehouseDevice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@ManyToOne
	@JoinColumn(name = "adapter_id")
	private Device device;

	@OneToMany(mappedBy = "warehouseDevice")
	private List<WarehouseDeviceInput> warehouseDeviceInputs;

	@OneToMany(mappedBy = "warehouseDevice")
	private List<WarehouseDeviceOutput> warehouseDeviceOutputs;

	@ManyToMany
	@JoinTable(name = "warehouses_devices_transfers_relationship", 
	joinColumns = @JoinColumn(name = "warehouses_device_id"), 
	inverseJoinColumns = @JoinColumn(name = "warehouses_devices_transfer_id"))
	private List<WarehouseDeviceTransfer> warehouseDeviceTransfers;

	@Column(nullable = true)
	private Long onHand;
}
