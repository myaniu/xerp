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

//入库单表
@Data
@Table("ERP_EnterStock")
public class EnterStock {

	@Id
	private Long id;/* 入库单编号, 主键 */
	@Column
	private Timestamp enterdate; /* 入库时间 */
	@Column
	private String batchno; /* 批次编号 */
	@Column
	private Long userid;/*入库人 ,外键 ( 参照User表)  */
	@Column
	private Long orgid;/* 入库部门 ,外键 ( 参照 Organization 表 ) */
	@Column
	private Long storehouseid;/* 所入仓库 ,外键 ( 参照 STOREHOUSE 表) */
	@One(target = User.class, field = "userid")
	private User users; /* 入库人 ,外键 ( 参照User表) */
	@One(target = Organization.class, field = "orgid")
	private Organization organisation; /* 入库部门 ,外键 ( 参照 Organization 表 ) */
	@One(target = StoreHouse.class, field = "storehouseid")
	private StoreHouse StoreHouse; /* 所入仓库 ,外键 ( 参照 STOREHOUSE 表) */
	@Many(target=EnterStockDetail.class,field="enterstockid")
	private List<EnterStockDetail> EnterStockDetailSet;// 


}