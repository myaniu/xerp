package com.xerp.domain.basic;



import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;



//供应商表
@Data
@Table("ERP_Supplier")
public class Supplier {
	
	
	@Id
	private Long id;//供应商编号
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String name;//供应商名称
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 200)
	private String Address;//地址
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String Phone;//电话
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String Fax;//传真
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String PostalCode;//邮编
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String ConstactPerson;//联系人

	
	
}
