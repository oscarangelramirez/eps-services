package com.epsglobal.services.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "warehouses_devices_transfers")
public class WarehouseDeviceTransfer {
	public enum Status {
		SENT, RECEIVED, CANCELLED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 8)
	@Enumerated(value = EnumType.STRING)
	private Status status;

	@ManyToMany(mappedBy = "warehouseDeviceTransfers")
	private List<WarehouseDevice> warehouseDevices;

	@OneToOne
	private WarehouseDeviceTransferPlace origin;

	@OneToOne
	private WarehouseDeviceTransferPlace destination;
}