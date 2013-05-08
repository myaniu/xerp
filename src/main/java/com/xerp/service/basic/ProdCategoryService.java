package com.xerp.service.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.service.IdEntityService;

import com.xerp.domain.basic.ProdCategory;


@IocBean(fields = { "dao" })
public class ProdCategoryService extends IdEntityService<ProdCategory> {
	public ProdCategoryService() {
		super();
	}
	public ProdCategoryService(Dao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
	public void update(ProdCategory obj) {
		dao().update(obj);
	}

	public ProdCategory view(Long id) {
		ProdCategory obj=fetch(Cnd.where("id", "=", id));
		obj=dao().fetchLinks(fetch(id), null);	
		return obj;
	}
	
	public List<ProdCategory> list() {
		return dao().fetchLinks(query(null, null), null);
	}
	
	public Map<String, Object> Pagerlist(int pageNum, int numPerPage,
			ProdCategory obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<ProdCategory> uiList = dao().fetchLinks(query(bulidQureyCnd(obj), pager),null);
		if (pager != null) {
			pager.setRecordCount(dao().count(ProdCategory.class, bulidQureyCnd(obj)));
			map.put("pager", pager);
		}
		map.put("o", obj);
		map.put("pagerlist", uiList);
		return map;
		
	}
	
	public void insert(ProdCategory obj) {
		dao().insert(obj);
	}

	
	public QueryResult getRoleListByPager(int pageNumber, int pageSize) {
		Pager pager = dao().createPager(pageNumber, pageSize);
		List<ProdCategory> list = dao().query(ProdCategory.class, null, pager);
		pager.setRecordCount(dao().count(ProdCategory.class));
		return new QueryResult(list, pager);
	}
	
	/**
	 * 构建查询条件
	 * 
	 * @param obj
	 * @return
	 */
	
	private Cnd bulidQureyCnd(ProdCategory obj) {
		Cnd cnd = null;
		if (obj != null) {
			cnd = Cnd.where("1", "=", 1);
			// 按名称查询
			if (!Strings.isEmpty(obj.getName()))
				cnd.and("name", "=", obj.getName());

		}
		return cnd;
	}
}
