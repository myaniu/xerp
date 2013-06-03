package com.nutzside.system.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.nutzside.common.domain.AjaxResData;
import com.nutzside.common.service.BaseService;
import com.nutzside.common.util.Webs;
import com.nutzside.system.domain.SysEnum;
import com.nutzside.system.domain.SysEnumItem;
import com.nutzside.system.domain.User;

@IocBean(args = { "refer:dao" })
public class SystemService extends BaseService<Object> {

	public static final String SYSTEM_SYSTEMNAME = "SYSTEM_SYSTEMNAME";

	public SystemService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AjaxResData getSystemName() {
		AjaxResData respData = new AjaxResData();
		String systemName = Webs.config().get(SYSTEM_SYSTEMNAME);
		if (Strings.isEmpty(systemName)) {
			respData.setError("配置文件中SYSTEM_SYSTEMNAME未配置或为空");
		} else {
			respData.setLogic(systemName);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData getSysEnumItemMap(String sysEnumName) {
		AjaxResData respData = new AjaxResData();
		SysEnum sysEnum = dao().fetch(SysEnum.class, Cnd.where("NAME", "=", sysEnumName));
		if (sysEnum == null) {
			StringBuilder message = new StringBuilder("系统枚举:\"");
			message.append(sysEnumName);
			message.append("\"不存在!");
			throw new RuntimeException(message.toString());
		}
		List<SysEnumItem> items = dao().query(SysEnumItem.class, Cnd.where("SYSENUMID", "=", sysEnum.getId()), null);
		Map<String, String> options = new LinkedHashMap<String, String>();
		for (SysEnumItem item : items) {
			options.put(item.getText(), item.getValue());
		}
		respData.setLogic(options);
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData getCurrentUserName(User cUser) {
		AjaxResData respData = new AjaxResData();
		if (cUser != null) {
			respData.setLogic(cUser.getName());
			respData.setInfo("用户" + cUser.getName() + "登录成功!");
		}
		return respData;
	}

	
}