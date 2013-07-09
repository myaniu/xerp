package com.nutzside.system.module;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.system.domain.User;
import com.nutzside.system.service.SystemService;

@IocBean
@At("/system/page")
public class PageModule {
	@Inject
	private SystemService systemService;
	@At
	@Ok("httl:page.page_header")
	public ResponseData header() {	
		User cUser = (User) SecurityUtils.getSubject().getSession().getAttribute("CurrentUser");
		return systemService.getCurrentUserName(cUser);
	}

	@At
	@Ok("httl:page.page_home")
	@RequiresAuthentication
	public void home() {
	}

	@At
	@Ok("httl:page.page_index")
	public void index() {
		
	}
	
	@At
	@Ok("jsp:page.page_menu")
	@RequiresAuthentication
	public void menu() {
	}
	
}
