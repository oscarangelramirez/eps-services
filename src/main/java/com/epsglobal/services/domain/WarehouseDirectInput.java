package com.epsglobal.services.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "warehouses_directs_inputs")
public class WarehouseDirectInput {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = true, length = 15)
	private String order;
	
	@Column(nullable = true, length = 1000)
	private String comments;
	
	@Column(nullable = true)
	private Integer quantity;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false)
	private BigDecimal cost;
	
	@Column(nullable = false)
	private BigDecimal profit;
	
	@OneToOne
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "warehouse_direct_id")
	private WarehouseDirect warehouseDirect;
}