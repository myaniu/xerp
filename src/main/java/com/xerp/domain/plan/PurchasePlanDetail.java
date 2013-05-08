package com.xerp.domain.plan;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.xerp.domain.apply.ApplyProductDetail;
import com.xerp.domain.basic.Product;

//采购计划明细
@Data
@Table("ERP_PurchasePlanDetail")
public class PurchasePlanDetail {
	@Id
	private Long id;/* 编号, 主键 */
	@Column
	private Long productid;/* 商品编号, 主键 */
	@Column
	private Long Quantity;// 此种商品数量
	@Column
	private float Price;// 此种商品参考价格
	@Column
	private int status;/* 状态*/
	private String taskId;
	private boolean isConflict;
	@Column
	private Long purchasePlanid;/* 编号, 主键 */
	@Column
	private Long applyProductDetailid;/* 编号, 主键 */
	@One(target = PurchasePlan.class, field = "purchasePlanid")
	private PurchasePlan purchasePlan; /*编号, 外键 ( 参照 PurchasePlan 表 ) */
	@One(target = Product.class, field = "productid")
	private Product product; /*编号, 外键 ( 参照 Product 表 ) */
	@One(target = ApplyProductDetail.class, field = "applyProductDetailid")
	private ApplyProductDetail applyProductDetail; /*编号, 外键 ( 参照 ApplyProductDetail 表 ) */
	
}
