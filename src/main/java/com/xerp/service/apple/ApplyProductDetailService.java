package com.xerp.service.apple;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import com.xerp.domain.apply.ApplyProductDetail;
@IocBean(fields = { "dao" })
public class ApplyProductDetailService extends IdEntityService<ApplyProductDetail>{
	public ApplyProductDetailService() {
		super();
	}
	public ApplyProductDetailService(Dao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
}
