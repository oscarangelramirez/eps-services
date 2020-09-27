package com.epsglobal.services.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String email;
	
	@Column(nullable = false, length = 1000)
	private String password;
	
	@Column(nullable = true)
	private Date lastChangeDate;
	
	@Column(nullable = true)
	private Integer numberAttemps;
	
	@Column(nullable = true)
	private Boolean isLocked;
	
	@Column(nullable = true)
	private Boolean isActive;
	
	@OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;
	
	@OneToMany(mappedBy = "user")
    private List<WarehouseUser> warehouseUsers;
}
