package com.epsglobal.services.domain;

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
@Table(name = "permissions")
public class Permission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String name;
	
	@Column(nullable = true, length = 1000)
	private String description;
	
	@Column(nullable = false, length = 255)
	private String module;
	
	@Column(nullable = false, length = 255)
	private String screen;
	
	@OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissions;
}
