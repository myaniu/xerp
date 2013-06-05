package com.xerp.domain.sell;

import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
//物料采购
@Data
@Table("ERP_SaleOrder")
public class SaleOrder {
	
	@Id
	@Name
	@Column
	private String saleordercode;// 项目订单编号
	@Column
	private String deliver;// 交付日期
	@Column
	private int balanceState;// 结算状态
	@Column
	private int inState;// 入库状态
	@Column
	private int backState;// 退状态
	@Column
	private String cause;//退回原因
	
	@Many(target=SaleOrderDetail.class,field="saleordercode")
	private List<SaleOrderDetail> SaleOrderDetailSet;// 
	
	
}
