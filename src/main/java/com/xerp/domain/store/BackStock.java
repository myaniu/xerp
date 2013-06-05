package com.xerp.domain.store;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.nutzside.system.domain.Organization;
import com.nutzside.system.domain.User;
//退库单表
@Data
@Table("ERP_BackStock")
public class BackStock {
	@Id
	private Long id;/* 退库单编号, 主键 */
	@Column
	private Timestamp BackDate; /* 入库时间 */
	@Column
	private String BatchNo; /* 批次编号 */
	@Column
	private Long UserId;/*退库人 ,外键 ( 参照User表)  */
	@Column
	private Long OrgId;/* 退库部门 ,外键 ( 参照 Organization 表 ) */
	@Column
	private Long StoreHouseId;/* 所入仓库 ,外键 ( 参照 STOREHOUSE 表) */
	@One(target = User.class, field = "UserId")
	private User users; /* 退库人 ,外键 ( 参照User表) */
	@One(target = Organization.class, field = "OrgId")
	private Organization organisation; /* 退库部门 ,外键 ( 参照 Organization 表 ) */
	@One(target = StoreHouse.class, field = "StoreHouseId")
	private StoreHouse storehouse; /* 所入仓库 ,外键 ( 参照 STOREHOUSE 表) */
	
	@Many(target=BackStockDetail.class,field="stockid")
	private List<BackStockDetail> BackStockDetailSet;// 
	
}
