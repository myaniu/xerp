package com.xcms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.xcms.domain.Article;
import com.xcms.service.ArticleService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/xcms/Article")
public class ArticleModule {
	
	@Inject
	private ArticleService ArticleService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") Article ArticleSearch) {
		return ArticleService.getGridData(jqReq, isSearch, ArticleSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Article Article) {
		return ArticleService.CUDArticle(oper, ids, Article);
	}
	
	@At
	@Ok("httl:xcms.Article_manager")
	@RequiresPermissions("httl:read:Article_manager")
	public void Article_manager() {
	}
	
}