package com.xcms.service;

import com.nutzside.common.domain.AjaxResData;
import com.nutzside.common.domain.jqgrid.AdvancedJqgridResData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.common.service.BaseService;
import com.nutzside.common.util.StrUtils;

import com.xcms.domain.Article;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

@IocBean(args = { "refer:dao" })
public class ArticleService extends BaseService<Article> {

	public ArticleService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Article> getGridData(JqgridReqData jqReq, Boolean isSearch, Article ArticleSearch) {
		Cnd cnd = null;
		if (isSearch && null != ArticleSearch) {
			cnd = Cnd.where("1", "=", 1);
			Long id = ArticleSearch.getId();
			if (null != id) {
				cnd.and("ID", "=", id);
			}
		}
		AdvancedJqgridResData<Article> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDArticle(String oper, String ids, Article Article) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setNotice("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(Article);
			respData.setNotice("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(Article);
			respData.setNotice("修改成功!");
		} else {
			respData.setError("未知操作!");
		}
		return respData;
	}
}