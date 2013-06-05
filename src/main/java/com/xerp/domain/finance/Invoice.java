package com.xerp.domain.finance;

import java.sql.Timestamp;

import lombok.Data;

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
	private String InvoiceNum;//发票编号 国家规定
	@Column
	private Timestamp InvoiceDate; /* 开票日期  */
	@Column
	private Double taxtotal;// 税收总额
	@Column
	private Double saletotal;// 销售总额
	@Column
	private int status;//状态
	@Column
	private Long customerID;///* 客户编号 ,外键 ( 参照 Customer 表 ) */
	@One(target = Customer.class, field = "customerID")
	private Customer Customer; /* 客户 ,外键 ( 参照 Customer 表) */
	@Column
	private Long orgId;
	@One(target = Organization.class, field = "orgId")
	private Organization organization; /* 部门 ,外键 ( 参照 Organization 表) */
	
	
}
