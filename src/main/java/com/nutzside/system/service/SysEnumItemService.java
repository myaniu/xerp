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
import com.nutzside.system.domain.SysEnumItem;

@IocBean(args = { "refer:dao" })
public class SysEnumItemService extends BaseService<SysEnumItem> {

	public SysEnumItemService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SysEnumItem> getGridData(JqgridReqData jqReq, Boolean isSearch,
			SysEnumItem sysEnumItemSearch) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != sysEnumItemSearch) {
			Long sysEnumId = sysEnumItemSearch.getSysEnumId();
			if (null != sysEnumItemSearch) {
				cnd = Cnd.where("SYSENUMID", "=", sysEnumId);
			}
			if (null != isSearch && isSearch) {
				String text = sysEnumItemSearch.getText();
				if (!Strings.isEmpty(text)) {
					cnd.and("TEXT", "LIKE", StrUtils.quote(text, '%'));
				}
				String value = sysEnumItemSearch.getValue();
				if (!Strings.isEmpty(value)) {
					cnd.and("VALUE", "LIKE", StrUtils.quote(value, '%'));
				}
			}
		}

		AdvancedJqgridResData<SysEnumItem> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDSysEnumItem(String oper, String ids, SysEnumItem sysEnumItem) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setInfo("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(sysEnumItem);
			respData.setInfo("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(sysEnumItem);
			respData.setInfo("修改成功!");
		} else {
			respData.setError("未知操作!");
		}
		return respData;
	}
}