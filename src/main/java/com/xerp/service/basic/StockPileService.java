package com.xerp.service.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import com.xerp.domain.store.StockPile;

@IocBean(fields = { "dao" })
public class StockPileService extends IdEntityService<StockPile> {
	public StockPileService() {
		super();
	}
	public StockPileService(Dao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
	
	public Map<String, Object> Pagerlist(int pageNum, int numPerPage,
			StockPile obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<StockPile> uiList = dao().fetchLinks(query(bulidQureyCnd(obj), pager),null);
		if (pager != null) {
			pager.setRecordCount(dao().count(StockPile.class, bulidQureyCnd(obj)));
			map.put("pager", pager);
		}
		map.put("o", obj);
		map.put("pagerlist", uiList);
		return map;
		
	}
	/**
	 * 构建查询条件
	 * @param obj
	 * @return
	 */
	private Cnd bulidQureyCnd(StockPile obj){
		Cnd cnd=null;
		if(obj!=null){
			cnd=Cnd.where("1", "=", 1);
	       
		}
		return cnd;
	}
}
