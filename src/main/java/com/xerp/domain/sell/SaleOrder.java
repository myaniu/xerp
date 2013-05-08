package com.xerp.domain.sell;

import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;
//物料采购
@Data
@Table("ERP_SaleOrder")
public class SaleOrder {
	
	@Id
	private Long id;/* 单编号, 主键 */
	
	private String deliver;// 交付日期
	@Column
	private String orderCode;// 项目订单编号
	@Column
	private String askCode;// 询价单编号
	@Column
	private int balanceState;// 结算状态
	@Column
	private int inState;// 入库状态
	@Column
	private int backState;// 退状态
	@Column
	private String cause;//退回原因
	@Column
	private String code;/* 编码*/
	@Many(target=SaleOrderDetail.class,field="orderid")
	private List<SaleOrderDetail> saleOrderDetailSet;// 
	
	
}
