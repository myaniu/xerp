package com.nutzside.system.domain;

import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_ROLE")
@Data
public class Role {
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 200)
	private String name;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;
	@ManyMany(target = User.class, relation = "SYSTEM_USER_ROLE", from = "ROLEID", to = "USERID")
	private List<User> users;
	@ManyMany(target = Permission.class, relation = "SYSTEM_ROLE_PERMISSION", from = "ROLEID", to = "PERMISSIONID")
	private List<Permission> permissions;
	@Column("is_system")
	@ColDefine(type = ColType.BOOLEAN, width = 1)
	private boolean system;


}
