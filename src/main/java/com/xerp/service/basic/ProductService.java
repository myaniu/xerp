package com.xerp.service.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.service.IdEntityService;

import com.xerp.domain.basic.Product;

@IocBean(fields = { "dao" })
public class ProductService extends IdEntityService<Product> {
	public ProductService() {
		super();
	}
	public ProductService(Dao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
	
	public Map<String, Object> Pagerlist(int pageNum, int numPerPage,
			Product obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<Product> uiList = dao().fetchLinks(query(bulidQureyCnd(obj), pager),null);
		if (pager != null) {
			pager.setRecordCount(dao().count(Product.class, bulidQureyCnd(obj)));
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
	private Cnd bulidQureyCnd(Product obj){
		Cnd cnd=null;
		if(obj!=null){
			cnd=Cnd.where("1", "=", 1);
	        //按编号查询
	        if(!Strings.isEmpty(obj.getCode()))
				cnd.and("code", "=", obj.getCode());
	        //按名称查询
	        if(!Strings.isEmpty(obj.getName()))
				cnd.and("name", "=", obj.getName());
	        //按规格查询
	        if(!Strings.isEmpty(obj.getSpecification()))
				cnd.and("specification", "=", obj.getSpecification());
	        //按单位查询
	        if(!Strings.isEmpty(obj.getUnit()))
				cnd.and("unit", "=", obj.getUnit());
	       
	        //按创建人查询
	        if(!Strings.isEmpty(obj.getCreateUser()))
				cnd.and("createUser", "=", obj.getCreateUser());
	        //按创建时间查询
	        if(!Strings.isEmpty(obj.getCreateDate()))
				cnd.and("createDate", "=", obj.getCreateDate());
	        //按修改人查询
	    	if(!Strings.isEmpty(obj.getModifyUser()))
				cnd.and("modifyUser", "=", obj.getModifyUser());
	        //按修改时间查询
	    	if(!Strings.isEmpty(obj.getModifyDate()))
				cnd.and("modifyDate", "=", obj.getModifyDate());
		}
		return cnd;
	}
}
