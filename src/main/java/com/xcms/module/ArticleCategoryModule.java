package com.xcms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.xcms.domain.ArticleCategory;
import com.xcms.service.ArticleCategoryService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/xcms/ArticleCategory")
public class ArticleCategoryModule {
	
	@Inject
	private ArticleCategoryService ArticleCategoryService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") ArticleCategory ArticleCategorySearch) {
		return ArticleCategoryService.getGridData(jqReq, isSearch, ArticleCategorySearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") ArticleCategory ArticleCategory) {
		return ArticleCategoryService.CUDArticleCategory(oper, ids, ArticleCategory);
	}
	
	@At
	@Ok("httl:xcms.ArticleCategory_manager")
	@RequiresPermissions("httl:read:ArticleCategory_manager")
	public void ArticleCategory_manager() {
	}
	
}