package com.xcms.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.nutzside.common.domain.AjaxResData;
import com.nutzside.common.domain.jqgrid.AdvancedJqgridResData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.common.service.BaseService;
import com.xcms.domain.ArticleCategory;

@IocBean(args = { "refer:dao" })
public class ArticleCategoryService extends BaseService<ArticleCategory> {

	public ArticleCategoryService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<ArticleCategory> getGridData(JqgridReqData jqReq, Boolean isSearch, ArticleCategory ArticleCategorySearch) {
		Cnd cnd = null;
		if (isSearch && null != ArticleCategorySearch) {
			cnd = Cnd.where("1", "=", 1);
			Long id = ArticleCategorySearch.getId();
			if (null != id) {
				cnd.and("ID", "=", id);
			}
		}
		AdvancedJqgridResData<ArticleCategory> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDArticleCategory(String oper, String ids, ArticleCategory ArticleCategory) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setNotice("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(ArticleCategory);
			respData.setNotice("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(ArticleCategory);
			respData.setNotice("修改成功!");
		} else {
			respData.setError("未知操作!");
		}
		return respData;
	}
}