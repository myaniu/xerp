package com.nutzside.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.Param;
import org.nutz.service.IdEntityService;

import com.nutzside.system.domain.Organization;

@IocBean(fields = { "dao" })
public class OrganizationService extends IdEntityService<Organization> {

	
	public OrganizationService() {
		super();
	}

	public OrganizationService(Dao dao) {
		super(dao);
	}
	
	public void insert(Organization Organization) {
		dao().insert(Organization);
	}


	public void update(Organization Organization) {
		dao().update(Organization);
	}
	
	

	public Map<String, Object> Pagerlist( int pageNum ,int numPerPage,@Param("..") Organization obj) {
		
		Pager pager = dao().createPager((pageNum<1)?1:pageNum, (numPerPage < 1)? 20:numPerPage);
		List<Organization> list = dao().query(Organization.class, bulidQureyCnd(obj), pager);
		Map<String, Object> map = new HashMap<String, Object>();
		if (pager != null) {
			pager.setRecordCount(dao().count(Organization.class,bulidQureyCnd(obj)));
			map.put("pager", pager);
		}
		map.put("o", obj);
		map.put("pagerlist", list);
		return map;

	}
	
	/**
	 * 构建查询条件
	 * @param obj
	 * @return
	 */
	private Cnd bulidQureyCnd(Organization obj){
		Cnd cnd=null;
		if(obj!=null){
			cnd=Cnd.where("1", "=", 1);
	        //按名称查询
	        if(!Strings.isEmpty(obj.getName()))
				cnd.and("name", "=", obj.getName());
	       
		}
		return cnd;
	}
}