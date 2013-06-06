package com.xerp.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.nutzside.system.domain.User;

//商品分类表
@Data
@Table("ERP_ProdCategory")
public class ProdCategory {
	@Id
	private Long id;// 商品分类编号
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 200)
	private String name;// 商品分类名称
	@Column
	public Long parentId;// 建分类人
	@Column
	public Long userid;// 建分类人
	@One(target = User.class, field = "userid")
	public User user;// 建分类人
	@Column
	private Timestamp CreateDate;// 建分类时间
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String Remark;// 描述,备注
	
	@One(target = ProdCategory.class,field="parentId")
	private ProdCategory parent;// 上级分类
	
	@Many(target=ProdCategory.class,field="parentId")
	private List<ProdCategory> children;// 下级分类
	
	
}
