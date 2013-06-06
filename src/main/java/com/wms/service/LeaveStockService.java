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
import com.wms.domain.LeaveStock;

@IocBean(args = { "refer:dao" })
public class LeaveStockService extends BaseService<LeaveStock> {

	public LeaveStockService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<LeaveStock> getGridData(JqgridReqData jqReq, Boolean isSearch, LeaveStock leaveStockSearch) {
		Cnd cnd = null;
		if (isSearch && null != leaveStockSearch) {
			cnd = Cnd.where("1", "=", 1);
			Long id = leaveStockSearch.getId();
			if (null != id) {
				cnd.and("ID", "=", id);
			}
		}
		AdvancedJqgridResData<LeaveStock> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDLeaveStock(String oper, String ids, LeaveStock leaveStock) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setNotice("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(leaveStock);
			respData.setNotice("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(leaveStock);
			respData.setNotice("修改成功!");
		} else {
			respData.setError("未知操作!");
		}
		return respData;
	}
}