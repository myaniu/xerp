package com.xerp.domain;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;


//客户表
@Data
@Table("ERP_Customer")
public class Customer {
	@Id
	private Long id;// 客户表编号
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String name;// 客户表名称
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 200)
	private String Address;// 地址
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String Phone;// 电话
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String Fax;// 传真
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String PostalCode;// 邮编
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String ConstactPerson;// 联系人



}
