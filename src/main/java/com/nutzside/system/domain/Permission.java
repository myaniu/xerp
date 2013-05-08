package com.nutzside.system.domain;

import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("SYSTEM_PERMISSION")
public class Permission {
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 200)
	private String name;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;
	@ManyMany(target = Role.class, relation = "SYSTEM_ROLE_PERMISSION", from = "PERMISSIONID", to = "ROLEID")
	private List<Role> roles;

}