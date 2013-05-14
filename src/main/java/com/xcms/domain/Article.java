package com.xcms.domain;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("cms_article")
public class Article {

	@Name
	private String id;

	@Column
	@ColDefine(type = ColType.TIMESTAMP)
	private Date createDate;

	@Column
	@ColDefine(type = ColType.TIMESTAMP)
	private Date modifyDate;
	@Column
	private String author;

	@Column
	@ColDefine(type = ColType.TEXT)
	private String content;

	@Column
	private int hits;

	@Column
	private String htmlPath;

	@Column
	@ColDefine(type = ColType.BOOLEAN, width = 1)
	private boolean ispublication;

	@Column
	@ColDefine(type = ColType.BOOLEAN, width = 1)
	private boolean isrecommend;

	@Column
	@ColDefine(type = ColType.BOOLEAN, width = 1)
	private boolean istop;

	@Column
	@ColDefine(type = ColType.TEXT)
	private String metaDescription;

	@Column
	@ColDefine(type = ColType.TEXT)
	private String metaKeywords;

	@Column
	private int pageCount;

	@Column
	private String title;

	@Column
	private String articleCategoryId;

	@One(target = ArticleCategory.class, field = "articleCategoryId")
	private ArticleCategory articleCategory;// 文章分类

	
}