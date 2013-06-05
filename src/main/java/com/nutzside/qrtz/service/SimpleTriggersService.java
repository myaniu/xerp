package com.nutzside.qrtz.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.nutzside.common.domain.AjaxResData;
import com.nutzside.common.domain.jqgrid.AdvancedJqgridResData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.common.service.BaseService;
import com.nutzside.qrtz.domain.SimpleTriggers;

@IocBean(args = { "refer:dao" })
public class SimpleTriggersService extends BaseService<SimpleTriggers> {

	public SimpleTriggersService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SimpleTriggers> getGridData(JqgridReqData jqReq, Boolean isSearch,
			SimpleTriggers simpleTriggersSearch) {
		Cnd cnd = null;
		//		if (isSearch && null != simpleTriggersSearch) {
		//			cnd = Cnd.where("1", "=", 1);
		//			Long id = simpleTriggersSearch.getId();
		//			if (null != id) {
		//				cnd.and("ID", "=", id);
		//			}
		//		}
		AdvancedJqgridResData<SimpleTriggers> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData getSimpleTrigger(String schedName, String triggerName, String triggerGroup) {
		AjaxResData resData = new AjaxResData();
		Condition cnd = Cnd.where("schedName", "=", schedName).and("triggerName", "=", triggerName)
				.and("triggerGroup", "=", triggerGroup);
		SimpleTriggers simpleTrigger = fetch(cnd);
		if (null == simpleTrigger) {
			resData.setInfo("获取触发器详情失败");
			return resData;
		}
		resData.setLogic(simpleTrigger);
		return resData;
	}
}