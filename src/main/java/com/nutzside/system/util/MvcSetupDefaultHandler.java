package com.nutzside.system.util;

import org.nutz.dao.Dao;

import com.nutzside.common.util.Webs;
import com.nutzside.system.domain.Organization;
import com.xerp.domain.apply.ApplyProduct;
import com.xerp.domain.apply.ApplyProductDetail;
import com.xerp.domain.basic.Customer;
import com.xerp.domain.basic.ProdCategory;
import com.xerp.domain.basic.Product;
import com.xerp.domain.basic.Supplier;
import com.xerp.domain.buy.BuyOrder;
import com.xerp.domain.buy.BuyOrderDetail;
import com.xerp.domain.finance.Invoice;
import com.xerp.domain.finance.Payment;
import com.xerp.domain.finance.Proceeds;
import com.xerp.domain.plan.PurchasePlan;
import com.xerp.domain.plan.PurchasePlanDetail;
import com.xerp.domain.sell.SaleOrder;
import com.xerp.domain.sell.SaleOrderDetail;
import com.xerp.domain.store.BackStock;
import com.xerp.domain.store.BackStockDetail;
import com.xerp.domain.store.EnterStock;
import com.xerp.domain.store.EnterStockDetail;
import com.xerp.domain.store.LeaveStock;
import com.xerp.domain.store.LeaveStockDetail;
import com.xerp.domain.store.StockPile;
import com.xerp.domain.store.StoreHouse;

public class MvcSetupDefaultHandler {


	/**
	 * 默认的应用启动时要做的操作
	 * 
	 * @param config
	 */
	public static void dolpTableInit() {
		Dao dao = Webs.dao();
		// 初始化系统基本的数据表
		
		dao.create(Organization.class, true);
		dao.create(ProdCategory.class, true);
		dao.create(Customer.class, true);
		dao.create(Product.class, true);
		dao.create(Supplier.class, true);
		dao.create(SaleOrder.class, true);
		dao.create(SaleOrderDetail.class, true);
		dao.create(BuyOrder.class, true);
		dao.create(BuyOrderDetail.class, true);
		dao.create(StoreHouse.class, true);
		dao.create(StockPile.class, true);
		dao.create(LeaveStock.class, true);
		dao.create(LeaveStockDetail.class, true);
		dao.create(EnterStock.class, true);
		dao.create(EnterStockDetail.class, true);
		dao.create(BackStock.class, true);
		dao.create(BackStockDetail.class, true);
		dao.create(Invoice.class, true);
		dao.create(Payment.class, true);
		dao.create(Proceeds.class, true);
		Organization org=new Organization();
		org.setCode("001");
		org.setName("测试");
		dao.insert(org);
		ProdCategory pc=new ProdCategory();
		pc.setName("测试1");
		dao.insert(pc);
		pc.setName("测试2");
		dao.insert(pc);
		pc.setName("测试3");
		pc.setParentId(1L);
		dao.insert(pc);
		pc.setName("测试4");
		pc.setParentId(2L);
		dao.insert(pc);
		dao.create(ApplyProduct.class, true);
		dao.create(ApplyProductDetail.class, true);
		dao.create(PurchasePlan.class, true);
		dao.create(PurchasePlanDetail.class, true);
	}

	/**
	 * 默认的应用停止时要做的操作
	 * 
	 * @param config
	 */
	public static void defaultDestroy() {

	}

}