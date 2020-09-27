package com.epsglobal.services.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "roles_permissions")
public class RolePermission {
	public enum Type{
		NOACCESS,
		READONLY,
		READWRITE
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 10)
	@Enumerated(value = EnumType.STRING)
	private Type type;
	
	@ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

	@ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
