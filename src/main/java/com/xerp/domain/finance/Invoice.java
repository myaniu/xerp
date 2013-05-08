package com.xerp.domain.finance;

import java.sql.Timestamp;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.nutzside.system.domain.Organization;
import com.xerp.domain.basic.Customer;
//发票
@Data
@Table("ERP_Invoice")
public class Invoice {
	@Id
	private Long id;//发票编号
	@Column
	private Timestamp InvoiceDate; /* 开票日期  */
	@Column
	private Double taxtotal;// 税收总额
	@Column
	private Double Saletotal;// 销售总额
	@Column
	private int status;//状态
	@Column
	private Long CustomerID;///* 客户编号 ,外键 ( 参照 Customer 表 ) */
	@One(target = Customer.class, field = "CustomerID")
	private Customer Customer; /* 客户 ,外键 ( 参照 Customer 表) */
	@Column
	@ColDefine(notNull = false, type = ColType.VARCHAR, width = 255)
	private Long OrgId;
	@One(target = Organization.class, field = "OrgId")
	private Organization organization; /* 部门 ,外键 ( 参照 Organization 表) */
	
	
}
