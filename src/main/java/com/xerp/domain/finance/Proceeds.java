package com.xerp.domain.finance;

import java.sql.Timestamp;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.xerp.domain.basic.Customer;
import com.xerp.domain.sell.SaleOrder;

//收款单
@Data
@Table("ERP_Proceeds")
public class Proceeds {
	@Id
	private long id;//收款单编号
	@Column
	private Timestamp ProceedsDate; /* 付款时间 */
	@Column
	private Long CustomerID;///* 客户编号 ,外键 ( 参照 Customer 表 ) */
	@Column
	private int Proceedstype; /* 收款类型 */
	@Column
	private Double price;// 金额
	@Column
	private  String  accept_no;//承兑编号
	@Column
	private int status;//状态
	@Column
	private String saleordercode;// 项目订单编号
	
	@One(target = Customer.class, field = "CustomerID")
	private Customer CustomerSet; /* 客户 ,外键 ( 参照 Customer 表) */
	
	@One(target = SaleOrder.class, field = "saleordercode")
	private SaleOrder SaleOrderSet; /* 销售,外键 ( 参照 SaleOrder 表) */
	

}
