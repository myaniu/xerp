package com.wms.domain;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

//物料采购明细
@Data
@Table("ERP_BuyOrderDetail")
public class BuyOrderDetail {
	
	@Id
	private Long id;/* 物料采购明细编号, 主键 */
	@Column
	private String productcode;/* 产品编号 */
	@Column
	private String buyordercode;/* 采购编号 */
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
	private Long supplierid;/* 供应商编号 */

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
	
	@One(target = Supplier.class, field = "supplierid")
	private Supplier Supplier; /* 商品编号, 外键 ( 参照 Supplier 表 ) */
	
	@One(target = Product.class, field = "productcode")
	private Product Product; /* 商品编号, 外键 ( 参照 PRODUCT 表 ) */
	
	@One(target = BuyOrder.class, field = "buyordercode")
	private BuyOrder BuyOrder; /* 物料采购编号, 外键 ( 参照 BuyOrder 表 ) */


}
