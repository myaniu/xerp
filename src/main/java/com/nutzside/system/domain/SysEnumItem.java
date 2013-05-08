package com.nutzside.system.domain;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("SYSTEM_SYSENUMITEM")
public class SysEnumItem {
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String text;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String value;
	@Column
	private Long sysEnumId;
	@One(target = SysEnum.class, field = "sysEnumId")
	private SysEnum sysEnum;

	
}