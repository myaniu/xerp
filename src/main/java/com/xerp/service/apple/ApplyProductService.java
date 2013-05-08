package com.xerp.service.apple;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import com.xerp.domain.apply.ApplyProduct;
@IocBean(fields = { "dao" })
public class ApplyProductService extends IdEntityService<ApplyProduct>{
	public ApplyProductService() {
		super();
	}
	public ApplyProductService(Dao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
}
