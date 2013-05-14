package com.xerp.domain.store;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.nutzside.system.domain.Organization;
import com.xerp.domain.basic.Product;

//库存表
@Data
@Table("ERP_StockPile")
public class StockPile {
	
	@Id
	private Long id;/* 库存编号 , 主键 */	
	@Column
	private Long orgid;/* 机构编号 , 主键 */
	@Column
	private Long storehouseid;/* 库房编号 , 主键 */
	@Column
	private Long productid;/* 产品编号 , 主键 */
	@Column
	private String batchno; /* 批次编号 */
	@Column
	private Long quantity; /* 所存数量 */
	@Column
	private float price; /* 参考价格 */
	@Column
	private int status;//状态
	@Column
	private int locknum;//锁住数量
	
	@One(target = Organization.class, field = "orgid")
	private Organization organizationset; /* 入库部门 ,外键 ( 参照 Organization 表 ) */
	
	@One(target = StoreHouse.class, field = "storehouseid")
	private StoreHouse storehouseset; /* 所入仓库 ,外键 ( 参照 STOREHOUSE 表) */
	
	@One(target = Product.class, field = "productid")
	private Product productset; /* 商品编号, 外键 ( 参照 PRODUCT 表 ) */

}