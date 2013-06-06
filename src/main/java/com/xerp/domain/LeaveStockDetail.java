package com.xerp.domain;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;


//仓库表
@Data
@Table("ERP_LeaveStockDetail")
public class LeaveStockDetail {
	
	@Column
	private Long id;/* 入库单编号, 主键 */
	@Column
	private String productcode;/* 商品编号, 主键 */
	@Column
	private Long quantity;// 此种商品数量
	@Column
	private float price;// 此种商品参考价格	
	@Column
	private Long leavestockid;/* 编号, 主键 */
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
	
	@One(target = LeaveStock.class, field = "leavestockid")
	private LeaveStock leaveStock; /*编号, 外键 ( 参照 LeaveStock 表 ) */
	@One(target = Product.class, field = "productcode")
	private Product product; /* 商品编号, 外键 ( 参照 PRODUCT 表 ) */
	
}