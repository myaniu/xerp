package com.wms.domain;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

//物料采购明细
@Data
@Table("ERP_SaleOrderDetail")
public class SaleOrderDetail {
	
	@Id
	private Long id;/* 物料采购明细编号, 主键 */
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
	
	@One(target = Product.class, field = "productcode")
	private Product Product; /* 商品编号, 外键 ( 参照 PRODUCT 表 ) */
	
	@One(target = SaleOrder.class, field = "saleordercode")
	private SaleOrder SaleOrder; /* 物料采购编号, 外键 ( 参照 BuyOrder 表 ) */

}
