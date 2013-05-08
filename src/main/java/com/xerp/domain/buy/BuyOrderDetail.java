package com.xerp.domain.buy;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.xerp.domain.basic.Product;
import com.xerp.domain.basic.Supplier;
import com.xerp.domain.plan.PurchasePlanDetail;
//物料采购明细
@Data
@Table("ERP_BuyOrderDetail")
public class BuyOrderDetail {
	
	@Id
	private Long id;/* 物料采购明细编号, 主键 */
	@Column
	private Long productid;/* 产品编号 */
	@Column
	private Long orderid;/* 物料采购编号 */
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
	@Column
	private Long purchasePlanDetailid;/* 编号, 主键 */
	@One(target = Supplier.class, field = "supplierid")
	private Supplier supplier; /* 商品编号, 外键 ( 参照 Supplier 表 ) */
	
	@One(target = Product.class, field = "productid")
	private Product product; /* 商品编号, 外键 ( 参照 PRODUCT 表 ) */
	
	@One(target = BuyOrder.class, field = "orderid")
	private BuyOrder buyOrder; /* 物料采购编号, 外键 ( 参照 BuyOrder 表 ) */

	@One(target = PurchasePlanDetail.class, field = "purchasePlanDetailid")
	private PurchasePlanDetail purchasePlanDetail; /*编号, 外键 ( 参照 PurchasePlanDetail 表 ) */
	
}
