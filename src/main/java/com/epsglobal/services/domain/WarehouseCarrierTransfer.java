package com.epsglobal.services.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "warehouses_carriers_transfers")
public class WarehouseCarrierTransfer {
	public enum Status{
		SENT,
		RECEIVED,
		CANCELLED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 8)
	@Enumerated(value = EnumType.STRING)
	private Status status;
	
	@ManyToMany
	@JoinTable(name = "warehouses_carriers_transfers_relationship", 
	joinColumns = @JoinColumn(name = "warehouses_carrier_id"), 
	inverseJoinColumns = @JoinColumn(name = "warehouses_carriers_transfer_id"))
	private List<WarehouseCarrier> warehouseCarriers;
	
	@OneToOne
	private WarehouseCarrierTransferPlace origin;
	
	@OneToOne
	private WarehouseCarrierTransferPlace destination;
}