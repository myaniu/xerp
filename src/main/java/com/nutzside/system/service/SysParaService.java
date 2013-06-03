package com.nutzside.system.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.nutzside.common.domain.AjaxResData;
import com.nutzside.common.domain.jqgrid.AdvancedJqgridResData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.common.service.BaseService;
import com.nutzside.common.util.StrUtils;
import com.nutzside.system.domain.SysPara;

@IocBean(args = { "refer:dao" })
public class SysParaService extends BaseService<SysPara> {

	public SysParaService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SysPara> getGridData(JqgridReqData jqReq, Boolean isSearch, SysPara sysParaSearch) {
		Cnd cnd = null;
		if (null != isSearch && isSearch && null != sysParaSearch) {
			cnd = Cnd.where("1", "=", 1);
			String name = sysParaSearch.getName();
			if (!Strings.isEmpty(name)) {
				cnd.and("NAME", "LIKE", StrUtils.quote(name, '%'));
			}
			String value = sysParaSearch.getValue();
			if (!Strings.isEmpty(value)) {
				cnd.and("VALUE", "=", value);
			}
			String description = sysParaSearch.getDescription();
			if (!Strings.isEmpty(description)) {
				cnd.and("DESCRIPTION", "LIKE", StrUtils.quote(description, '%'));
			}
		}
		AdvancedJqgridResData<SysPara> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDSysPara(String oper, String ids, SysPara sysPara) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setInfo("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(sysPara);
			respData.setInfo("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(sysPara);
			respData.setInfo("修改成功!");
		} else {
			respData.setError("未知操作!");
		}
		return respData;
	}
}