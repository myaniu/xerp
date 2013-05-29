package com.nutzside.system.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean
@At("/system/page")
public class PageModule {


	@At
	@Ok("fm:page.page_main")
	@RequiresAuthentication
	public void main() {
	}

	@At
	@Ok("httl:page.page_header")
	@RequiresAuthentication
	public Object header(HttpServletResponse response) throws IOException {
		
		Subject currentUser = SecurityUtils.getSubject();
		if(!Strings.isEmpty( currentUser.getPrincipal().toString())  )
        {  
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("currentUser", currentUser.getPrincipal().toString());
			return parameters;
        }  
		else{
			 response.sendRedirect("index.jsp");
			 return null;
		}
	}

	@At
	@Ok("fm:page.page_middle")
	@RequiresAuthentication
	public void middle() {
	}

	@At
	@Ok("fm:page.page_index")
	@RequiresAuthentication
	public void index() {
		
	}
	
	
	@At
	@Ok("httl:page.menu_setting")
	@RequiresAuthentication
	public void setting() {
	}
}
