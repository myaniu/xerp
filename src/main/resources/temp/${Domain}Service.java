package ${package}.service;

import com.nutzside.common.domain.AjaxResData;
import com.nutzside.common.domain.jqgrid.AdvancedJqgridResData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.common.service.BaseService;
importcom.nutzside.common.util.StringUtils;

import ${domainPackage}.${Domain};

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

@IocBean(args = { "refer:dao" })
public class ${Domain}Service extends BaseService<${Domain}> {

	public ${Domain}Service(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<${Domain}> getGridData(JqgridReqData jqReq, Boolean isSearch, ${Domain} ${Domain?uncap_first}Search) {
		Cnd cnd = null;
		if (isSearch && null != ${Domain?uncap_first}Search) {
			cnd = Cnd.where("1", "=", 1);
			Long id = ${Domain?uncap_first}Search.getId();
			if (null != id) {
				cnd.and("ID", "=", id);
			}
		}
		AdvancedJqgridResData<${Domain}> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUD${Domain}(String oper, String ids, ${Domain} ${Domain?uncap_first}) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setNotice("删除成功!", null, null);
		} else if ("add".equals(oper)) {
			dao().insert(${Domain?uncap_first});
			respData.setNotice("添加成功!", null, null);
		} else if ("edit".equals(oper)) {
			dao().update(${Domain?uncap_first});
			respData.setNotice("修改成功!", null, null);
		} else {
			respData.setError(null, "未知操作!", null);
		}
		return respData;
	}
}