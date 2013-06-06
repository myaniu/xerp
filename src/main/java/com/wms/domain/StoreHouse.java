package com.wms.domain;

import java.sql.Timestamp;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.nutzside.system.domain.User;

//仓库表
@Data
@Table("ERP_StoreHouse")
public class StoreHouse {

	@Id
	private Long id;/* 仓库编号, 主键 */
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 200)
	private String Name; /* 仓库名称 */
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 200)
	private String Address; /* 地址 */
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String Phone; /* 电话 */
	@Column
	private int status;//状态
	@Column
	private Timestamp CreateDate; /* 仓库成立时间 */
	@Column
	private Long UserId;/* 仓库保管 ,外键 ( 参照User表) */
	
	@One(target = User.class, field = "UserId")
	private User users;/* 仓库保管 ,外键 ( 参照User表) */


}