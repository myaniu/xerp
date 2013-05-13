package com.nutzside.system.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.nutzside.common.mvc.view.JPEGView;
import com.nutzside.common.web.ajax.Ajax;
import com.nutzside.common.web.ajax.AjaxReturn;
import com.nutzside.system.domain.User;
import com.nutzside.system.service.UserService;


@At("/system")
@IocBean
public class SystemModule {

	@Inject
	private UserService userService;
	
	
	
	@At
	public User me() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}

	@At
	@Ok("jsp:page.login")
	@Fail("redirect:/index.jsp")
	public void  bindex(HttpServletResponse response){
		
	}
	
	
	@At
	@Fail(">>:/index.jsp")
	@Ok("json")
	public AjaxReturn login(@Param("name") String name,
			@Param("passwd") String passwd,
			@Param("remeberMe") boolean remeberMe, @Param("code") String code,
			HttpServletRequest request) {
		if (Strings.isBlank(name)) {
			return Ajax.fail().setMessage("请输入您的用户名!");
		} else if (Strings.isBlank(passwd)) {
			return Ajax.fail().setMessage("请输入您的密码!");
		} else if (Strings.isBlank(code)) {
			return Ajax.fail().setMessage("请输入您的验证码!");
		} else {
			String auth = StringUtils.upperCase(code);
			try {
				
				String CAPTCHA= StringUtils.upperCase(Mvcs.getHttpSession(true).getAttribute(JPEGView.CAPTCHA).toString());
				
				if (auth.equals(CAPTCHA)) {
					Subject subject = SecurityUtils.getSubject();
					UsernamePasswordToken token = new UsernamePasswordToken(
							name, passwd);
					token.setRememberMe(remeberMe);
					subject.login(token);
					return Ajax.ok();
				} else {
					return Ajax.fail().setMessage("验证码错误");
				}
			} catch (LockedAccountException e) {
				return Ajax.fail().setMessage("帐号已被锁定");
			} catch (AuthenticationException e) {
				return Ajax.fail().setMessage("密码错误或用户不存在");
			} catch (Exception e) {
				e.printStackTrace();
				return Ajax.fail().setMessage("登录失败");
			}
		}
	}


	@At
	@Ok("httl:page.main")
	@Fail("redirect:/index.jsp")
	public Object main(HttpServletResponse response) throws IOException {
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
	@RequiresAuthentication
	public void logout(HttpServletResponse response) throws IOException {
		SecurityUtils.getSubject().logout();
		// TODO
		// 用nutz重定向视图总是报异常org.apache.shiro.session.UnknownSessionException: There is no session with id...
		// 所以暂时用这种方式重定向
		response.sendRedirect("index.jsp");
	}

	
}