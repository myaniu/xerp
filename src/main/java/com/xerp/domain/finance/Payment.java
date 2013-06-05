package com.xerp.domain.finance;

import java.sql.Timestamp;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.xerp.domain.basic.Supplier;
import com.xerp.domain.buy.BuyOrder;

//付款单
@Data
@Table("ERP_Payment")
public class Payment {
	@Id
	private Long id;//付款单编号
	@Column
	private Timestamp PaymentDate; /* 付款时间 */
	@Column
	private Long SupplierID;///* 供应商编号 ,外键 ( 参照 Supplier 表 ) */
	@Column
	private String buyordercode;///*订单编号 ,外键 ( 参照 BuyOrder 表 ) */
	@Column
	private int Paymenttype; /* 支付类型 */
	@Column
	private Double price;// 金额
	@Column
	private  String  accept_no;//承兑编号
	@Column
	private int status;//状态
	
	@One(target = Supplier.class, field = "SupplierID")
	private Supplier SupplierSet; /* 供应商,外键 ( 参照 Supplier 表) */
	
	@One(target = BuyOrder.class, field = "buyordercode")
	private BuyOrder BuyorderSet; /* 购买单 ,外键 ( 参照 BuyOrder 表) */
	
	
	
	
}
