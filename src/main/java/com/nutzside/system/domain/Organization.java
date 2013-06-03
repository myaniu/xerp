package com.nutzside.system.domain;

import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("SYSTEM_Organization")
public class Organization {
	
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String code;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String name;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;
	@Column
	private Long parentOrgId;
	@One(target = Organization.class, field = "parentOrgId")
	private Organization parentOrg;
	@Many(target = Organization.class, field = "parentOrgId")
	private List<Organization> childrenOrgs;
	@Many(target = Role.class, field = "organizationId")
	private List<Role> roles;

	
	

}