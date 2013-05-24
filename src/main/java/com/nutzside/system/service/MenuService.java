package com.nutzside.system.service;

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

import com.nutzside.system.domain.Menu;

@IocBean(fields = { "dao" })
public class MenuService extends IdEntityService<Menu>{
	public MenuService() {
		super();
	}

	public MenuService(Dao dao) {
		super(dao);
	}
	
	
	public void update(Menu obj) {
		dao().update(obj);
	}

	public Menu view(Long id) {
		Menu obj=fetch(Cnd.where("id", "=", id));
		obj=dao().fetchLinks(fetch(id), null);	
		return obj;
	}
	
	public List<Menu> list() {
		return dao().fetchLinks(query(null, null), null);
	}
	
	public Map<String, Object> Pagerlist(int pageNum, int numPerPage,
			Menu obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<Menu> uiList = dao().fetchLinks(query(bulidQureyCnd(obj), pager),null);
		if (pager != null) {
			pager.setRecordCount(dao().count(Menu.class, bulidQureyCnd(obj)));
			map.put("pager", pager);
		}
		map.put("o", obj);
		map.put("pagerlist", uiList);
		return map;
		
	}
	
	public void insert(Menu obj) {
		dao().insert(obj);
	}

	
	public QueryResult getRoleListByPager(int pageNumber, int pageSize) {
		Pager pager = dao().createPager(pageNumber, pageSize);
		List<Menu> list = dao().query(Menu.class, null, pager);
		pager.setRecordCount(dao().count(Menu.class));
		return new QueryResult(list, pager);
	}
	
	/**
	 * 构建查询条件
	 * 
	 * @param obj
	 * @return
	 */
	
	private Cnd bulidQureyCnd(Menu obj) {
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
