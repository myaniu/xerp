package com.nutzside.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import com.nutzside.system.domain.SysEnumItem;

@IocBean(fields = { "dao" })
public class SysEnumItemService extends IdEntityService<SysEnumItem> {
	public SysEnumItemService() {
		super();
	}

	public SysEnumItemService(Dao dao) {
		super(dao);
	}

	public List<SysEnumItem> list() {
		return query(null, null);
	}

	
	
	public Map<String, Object> Pagerlist(int pageNum, int numPerPage,
			SysEnumItem obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<SysEnumItem> uiList = dao().fetchLinks(query(bulidQureyCnd(obj), pager),null);
		if (pager != null) {
			pager.setRecordCount(dao().count(SysEnumItem.class, bulidQureyCnd(obj)));
			map.put("pager", pager);
		}
		map.put("o", obj);
		map.put("pagerlist", uiList);
		return map;
		
	}
	
	public void insert(SysEnumItem SysEnumItem) {
		dao().insert(SysEnumItem);
	}

	public SysEnumItem view(Long id) {
		return fetch(id);
	}

	public void update(SysEnumItem SysEnumItem) {
		dao().update(SysEnumItem);
	}
	/**
	 * 构建查询条件
	 * @param obj
	 * @return
	 */
	private Cnd bulidQureyCnd(SysEnumItem obj){
		Cnd cnd=null;
		if(obj!=null){
			cnd=Cnd.where("1", "=", 1);
	        //按名称查询
	        if(obj.getId()!=null)
				cnd.and("id", "=", obj.getId());
	       
		}
		return cnd;
	}
}
