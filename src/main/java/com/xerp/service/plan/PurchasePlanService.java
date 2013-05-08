package com.xerp.service.plan;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import com.xerp.domain.plan.PurchasePlan;
@IocBean(fields = { "dao" })
public class PurchasePlanService extends IdEntityService<PurchasePlan>{
	public PurchasePlanService() {
		super();
	}
	public PurchasePlanService(Dao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
}
