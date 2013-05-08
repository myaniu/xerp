package com.xerp.domain.apply;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.xerp.domain.basic.Product;

//申请产品单明细
@Data
@Table("ERP_ApplyProductDetail")
public class ApplyProductDetail {
	@Id
	private Long id;/* 编号, 主键 */
	@Column
	private Long productid;/* 商品编号, 主键 */
	@Column
	private Long Quantity;// 此种商品数量
	@Column
	private float Price;// 此种商品参考价格
	@Column
	private Long applyid;/* 编号, 主键 */
	@Column
	private int status;/* 状态*/
	private String taskId;
	private boolean isConflict;
	@One(target = ApplyProduct.class, field = "applyid")
	private ApplyProduct applyproduct; /*编号, 外键 ( 参照 ApplyProduct 表 ) */
	@One(target = Product.class, field = "productid")
	private Product product; /*编号, 外键 ( 参照 Product 表 ) */

}