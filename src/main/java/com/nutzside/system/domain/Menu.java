package com.nutzside.system.domain;

import java.util.List;
import java.util.Set;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("SYSTEM_MENU")
public class Menu {
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String name;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String url;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;
	@Column
	private Long parentId;

	@ManyMany(target = Role.class, relation = "SYSTEM_ROLE_MENU", from = "MENUID", to = "ROLEID")
	private List<Role> roles;
	
	
	@One(target = Menu.class,field="parentId")
	private Menu parent;// 上级分类
	
	@Many(target=Menu.class,field="id")
	private Set<Menu> children;// 下级分类
	
	
}