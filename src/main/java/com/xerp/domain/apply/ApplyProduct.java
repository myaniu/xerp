package com.xerp.domain.apply;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.nutzside.system.domain.User;

//申请产品单
@Data
@Table("ERP_ApplyProduct")
public class ApplyProduct {

	@Id
	private Long id;/* 申请单编号, 主键 */
	@Column
	private Date applyDate; /* 申请时间 */
	@Column
	private Long UserId;/*申请人 ,外键 ( 参照User表)  */
	@Column
	private Long type;/* 申请类型 如部门申请采购申请 */
	@Column
	private String reason;/* 申请原因*/
	@Column
	private String code;/* 编码*/
	@One(target = User.class, field = "UserId")
	private User users; /* 申请人 ,外键 ( 参照User表) */
	@Many(target=ApplyProductDetail.class,field="applyid")
	private List<ApplyProductDetail> applyproductDetailSet;// 
}