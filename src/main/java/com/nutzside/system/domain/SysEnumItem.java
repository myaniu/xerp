package com.nutzside.system.domain;

import java.util.Set;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("SYSTEM_SYSENUMITEM")
public class SysEnumItem {
	@Id
	private Long id;//主键
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String value;//码值
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 1000)
	private String remark;//描述
	@Column
	private Long parentId;
	@Column
	private Integer status;
	
	
	@One(target = SysEnumItem.class,field="parentId")
	private SysEnumItem parent;// 上级分类
	
	@Many(target=SysEnumItem.class,field="id")
	private Set<SysEnumItem> children;// 下级分类
	
}