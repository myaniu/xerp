package com.xerp.domain.store;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

//仓库表
@Data
@Table("ERP_BackStockDetail")
@PK({ "BackStockID", "ProductID" })
public class BackStockDetail {
	@Column
	private Long BackStockID;/* 入库单编号, 主键 */
	@Column
	private Long ProductID;/* 商品编号, 主键 */
	@Column
	private Long Quantity;// 此种商品数量
	@Column
	private float Price;// 此种商品参考价格
	@Column
	private int HaveInvoice;// 此种商品有没有开发票
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 200)
	private String InvoiceNum;// 发票号
	@Column
	private Long stockid;/* 编号, 主键 */
	@One(target = BackStock.class, field = "stockid")
	private BackStock backStock; /*编号, 外键 ( 参照 BackStock 表 ) */


}