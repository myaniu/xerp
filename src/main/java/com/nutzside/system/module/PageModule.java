package com.nutzside.system.module;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean
@At("/system/page")
public class PageModule {

	@At
	@Ok("httl:page.page_header")
	public void header() {

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
