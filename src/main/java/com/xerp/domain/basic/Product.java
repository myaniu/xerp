package com.xerp.domain.basic;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.nutzside.system.domain.Organization;


/**
* 产品设置 bean<br>
* 表名：ERP_PRODUCT<br>
* @author Dawn  email: csg0328#gmail.com
* @date 2011-11-22
* @version 1.0
*/
@Data
@Table("ERP_PRODUCT")
public class Product implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//columns START
	/** id **/
	
	@Id
	private Long id;
	/** 编号 **/
	@Column
	private String code;
	/** 名称 **/
	@Column
	private String name;
	/** 规格 **/
	@Column
	private String specification;
	/** 单位 **/
	@Column
	private String unit;
	
	/** 创建人 **/
	@Column
	private String createUser;
	/** 创建时间 **/
	@Column
	private String createDate;
	/** 修改人 **/
	@Column
	private String modifyUser;
	/** 修改时间 **/
	@Column
	private String modifyDate;
	@Column
	private Long orgid;
	@Column
	private Long sortid;/* 产品分类编号 , 主键 */
	@Column
	private int status;//状态

	@One(target = ProdCategory.class, field = "sortid")
	private ProdCategory category; /* 产品分类,外键 ( 参照 ProdCategory 表 ) */
	@One(target = Organization.class, field = "orgid")
	private Organization org; /* 部门 ,外键 ( 参照 Organization 表) */
	
}