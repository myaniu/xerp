package com.nutzside.system.domain;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("SYSTEM_SYSPARA")
public class SysPara {
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 100)
	private String name;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String value;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;

	
}