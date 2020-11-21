package com.epsglobal.services.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "warehouses_devices_transfers_places")
public class WarehouseDeviceTransferPlace {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private Date date;

	@Column(nullable = true, length = 100)
	private String comments;

	@Column(nullable = true)
	private Integer quantity;

	@OneToOne
	private Warehouse warehouse;
	
	@OneToOne
	private Device device;

	@OneToOne
	private User user;
}