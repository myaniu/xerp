package com.xerp.domain.audit;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
//审核意见
@Data
@Table("ERP_AuditMind")
public class AuditMind {
	@Id
	private Long id;// 记录ID
	@Column
	private Long sort;// 类型
	@Column
	private Long state;// 审核结果
	@Column
	private String auditDate;// 审核日期
	@Column
	public Long userid;// 审核用户
	@Column
	private String auditMinddesc;// 审核意见描述
}
