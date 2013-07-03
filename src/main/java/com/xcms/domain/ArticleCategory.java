package com.xcms.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("cms_article_category")
public class ArticleCategory {

	@Name
	private Long id;
	@Column
	@ColDefine(type = ColType.TIMESTAMP)
	private Date createDate;
	@Column
	@ColDefine(type = ColType.TIMESTAMP)
	private Date modifyDate;
	@Column
	private int grade;
	@Column
	@ColDefine(type = ColType.TEXT)
	private String metaDescription;
	@Column
	@ColDefine(type = ColType.TEXT)
	private String metaKeywords;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 225)
	private String name;
	@Column
	private int orderList;
	@Column
	@ColDefine(type = ColType.TEXT)
	private String path;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 225)
	private String sign;

	@Column
	private String parentId;

	@One(target = ArticleCategory.class, field = "parentId")
	private ArticleCategory parent;// 上级分类

	@Many(target = ArticleCategory.class, field = "id")
	private Set<ArticleCategory> children;// 下级分类

	@Many(target = Article.class, field = "articleCategoryId")
	private List<Article> articleSet;// 文章


	// @Transient
	public String getUrl() {
		return "/art/list/" + this.getId() + "." + "htm";
	}
}
