package com.xerp.domain;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;


//退库表明细
@Data
@Table("ERP_BackStockDetail")
public class BackStockDetail {

	@Id
	private Long id;/*明细编号, 主键 */
	@Column
	private String productcode;/* 产品编号 */
	@Column
	private String saleordercode;/* 销售编号 */
	@Column
	private String name;// 名称
	@Column
	private String spec;// 规格
	@Column
	private String unit;// 单位
	@Column
	private Double amount;// 数量
	@Column
	private Double inAmount;// 已入库数量
	@Column
	private Double price;// 单价
	@Column
	private String remark;// 备注
	@Column
	private int status;/* 状态*/
	@Column
	private Long Customerid;/* 客户编号 */
	@Column
	private String InvoiceNum;// 发票号
	@Column
	private Long backstockid;/* 退库编号, 主键 */	
	
	/** 创建人 **/
	@Column
	private String createUser;
	/** 创建时间 **/
	@Column
	private String createDate;
	/** 修改人 **/
	@Column
	private String modifyUser;
	/** 修改时间 **/
	@Column
	private String modifyDate;
	
	@One(target = Customer.class, field = "Customerid")
	private Customer Customer; /* 客户编号, 外键 ( 参照 Customer 表 ) */
	@One(target = Invoice.class, field = "InvoiceNum")
	private Invoice Invoice; /* 发票编号, 外键 ( 参照 Invoice 表 ) */
	@One(target = Product.class, field = "productcode")
	private Product Product; /* 商品编号, 外键 ( 参照 PRODUCT 表 ) */
	@One(target = BackStock.class, field = "backstockid")
	private BackStock backStock; /*编号, 外键 ( 参照 BackStock 表 ) */
	@One(target = SaleOrder.class, field = "saleordercode")
	private SaleOrder SaleOrder; /*销售编号, 外键 ( 参照 SaleOrder 表 ) */

}