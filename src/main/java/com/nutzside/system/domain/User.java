package com.nutzside.system.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

//alter table shiro.system_user add  locked bit(1) NOT NULL default 0;
@Data
@Table("SYSTEM_USER")
public class User {

	@Id
	private Long id;
	@Column
	private String username;
	@Column
	private String password;
	@Column("login_ip")
	private String loginIP;
	@Column
	private String name;
	@Column
	private String email;
	@Column("is_account_enabled")
	@ColDefine(notNull = false, type = ColType.BOOLEAN, width = 1)
	private Boolean accountEnabled;

	@Column("locked_date")
	@ColDefine(notNull = false, type = ColType.TIMESTAMP)
	private Date lockedDate;
	@Column("is_account_expired")
	@ColDefine(notNull = false, type = ColType.BOOLEAN, width = 1)
	private Boolean accountExpired;

	@Column("is_account_locked")
	@ColDefine(notNull = false, type = ColType.BOOLEAN, width = 1)
	private Boolean accountLocked;

	@Column("is_credentials_expired")
	@ColDefine(notNull = false, type = ColType.BOOLEAN, width = 1)
	private Boolean credentialsExpired;

	@Column("login_failure_count")
	@ColDefine(notNull = false, type = ColType.BOOLEAN, width = 11)
	private Integer loginFailureCount;

	@ColDefine(notNull = false, type = ColType.TIMESTAMP)
	@Column("create_date")
	private Date createDate;

	@Column("modify_date")
	@ColDefine(notNull = false, type = ColType.TIMESTAMP)
	private Date modifyDate;

	@Column("login_date")
	@ColDefine(notNull = false, type = ColType.TIMESTAMP)
	private Date loginDate;
	@Column("department")
	@ColDefine(notNull = false, type = ColType.VARCHAR, width = 255)
	private Long department;
	@Column
	@ColDefine(type = ColType.CHAR, width = 24)
	private String salt;
	@Column
	private Integer age;
	@ManyMany(target = Role.class, relation = "SYSTEM_USER_ROLE", from = "USERID", to = "ROLEID")
	private List<Role> roles;
	@One(target = Organization.class, field = "department")
	private Organization org; /* 部门 ,外键 ( 参照 Organization 表) */
	


}
