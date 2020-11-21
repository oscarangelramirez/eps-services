package com.epsglobal.services.domain;

import java.math.BigDecimal;
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
@Table(name = "warehouses_adapters_transfers_places")
public class WarehouseAdapterTransferPlace {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private Date date;

	@Column(nullable = true, length = 100)
	private String comments;

	@Column(nullable = true)
	private Integer quantity;

	@Column(nullable = false)
	private BigDecimal cost;

	@OneToOne
	private Warehouse warehouse;
	
	@OneToOne
	private Adapter adapter;

	@OneToOne
	private User user;
}