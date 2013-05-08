package com.xerp.service.buy;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import com.xerp.domain.buy.BuyOrder;
@IocBean(fields = { "dao" })
public class BuyOrderService extends IdEntityService<BuyOrder>{
	public BuyOrderService() {
		super();
	}
	public BuyOrderService(Dao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
}
