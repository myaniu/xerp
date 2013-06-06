package com.wms.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.nutzside.common.domain.AjaxResData;
import com.nutzside.common.domain.jqgrid.AdvancedJqgridResData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.common.service.BaseService;
import com.wms.domain.SaleOrder;

@IocBean(args = { "refer:dao" })
public class SaleOrderService extends BaseService<SaleOrder> {

	public SaleOrderService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SaleOrder> getGridData(JqgridReqData jqReq, Boolean isSearch, SaleOrder saleOrderSearch) {
		Cnd cnd = null;
		if (isSearch && null != saleOrderSearch) {
			cnd = Cnd.where("1", "=", 1);
			Long id = saleOrderSearch.getId();
			if (null != id) {
				cnd.and("ID", "=", id);
			}
		}
		AdvancedJqgridResData<SaleOrder> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDSaleOrder(String oper, String ids, SaleOrder saleOrder) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setNotice("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(saleOrder);
			respData.setNotice("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(saleOrder);
			respData.setNotice("修改成功!");
		} else {
			respData.setError("未知操作!");
		}
		return respData;
	}
}