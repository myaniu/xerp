package com.xerp.domain.store;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.xerp.domain.basic.Product;

//仓库表
@Data
@Table("ERP_EnterStockDetail")
public class EnterStockDetail {
	@Id
	private Long id;/* 入库单明细编号, 主键 */
	@Column
	private String productcode;/* 商品编号, 主键 */
	@Column
	private Long quantity;// 此种商品数量
	@Column
	private float price;// 此种商品参考价格
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
	@Column
	private Long enterstockid;/* 编号, 主键 */
	@One(target = EnterStock.class, field = "enterstockid")
	private EnterStock enterStock; /*编号, 外键 ( 参照 EnterStock 表 ) */
	@One(target = Product.class, field = "productcode")
	private Product product; /* 商品编号, 外键 ( 参照 PRODUCT 表 ) */
	

}