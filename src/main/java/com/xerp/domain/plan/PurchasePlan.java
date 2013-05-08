package com.xerp.domain.plan;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.nutzside.system.domain.Organization;
import com.nutzside.system.domain.User;

//采购计划
@Data
@Table("ERP_PurchasePlan")
public class PurchasePlan implements java.io.Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;/* 采购计划编号, 主键 */
	@Column
	private Date planDate; /* 申请时间 */
	@Column
	private Long type;/* 申请类型 如部门申请采购申请 */
	@Column
	private String code;/* 编码*/
	@Column
	private Long UserId;/*计划人 ,外键 ( 参照User表)  */
	@Column
	private Long OrgId;/* 计划部门 ,外键 ( 参照 Organization 表 ) */
	@One(target = User.class, field = "UserId")
	private User users; /* 入库人 ,外键 ( 参照User表) */
	@One(target = Organization.class, field = "OrgId")
	private Organization organisation; /* 入库部门 ,外键 ( 参照 Organization 表 ) */
	@Many(target=PurchasePlanDetail.class,field="purchasePlanid")
	private List<PurchasePlanDetail> purchasePlanDetail;// 
	
}
