package com.nutzside.system.module;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.nutzside.common.domain.AjaxResData;
import com.nutzside.common.domain.ResponseData;
import com.nutzside.system.domain.User;
import com.nutzside.system.service.ClientService;
import com.nutzside.system.service.SystemService;
import com.nutzside.system.service.UserService;

@IocBean
@At("/system/page")
public class SystemModule {

	@Inject
	private SystemService systemService;
	@Inject
	private UserService userService;
	@Inject
	private ClientService clientService;

	@At
	@RequiresAuthentication
	public ResponseData getSystemName() {
		return systemService.getSystemName();
	}

	@At("/getSysEnumItemMap/*")
	@RequiresAuthentication
	public ResponseData getSysEnumItemMap(String sysEnumName) {
		return systemService.getSysEnumItemMap(sysEnumName);
	}

	/**
	 * dummy existing url, 用于 jqGrid 的 CRUD on Local Data
	 */
	@At
	public void doNothing() {
	}

	@At
	@Fail(">>:/index.jsp")
	@Ok("json")
	public AjaxResData logon(@Param("num") String number, @Param("pwd") String password,
			@Param("rememberMe") boolean rememberMe, HttpServletRequest request) {
		AjaxResData respData = new AjaxResData();
		String host = request.getRemoteHost();
		AuthenticationToken token = new UsernamePasswordToken(number, password, rememberMe, host);
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			User user = userService.fetchByNumber(number);
			Session session = subject.getSession();
			session.setAttribute("CurrentUser", user);
			long[] permissionIdArr = userService.getCurrentPermissionIdList(user.getId());
			session.setAttribute("CurrentPermission", permissionIdArr);

			// 将该session相应的登录信息放入在线用户表中
			clientService.insert(session, request);
			return respData;
		} catch (UnknownAccountException e) {
			respData.setError("用户不存在");
			return respData;
		} catch (AuthenticationException e) {
			respData.setError("验证错误");
			return respData;
		} catch (Exception e) {
			respData.setError("登录失败"+e);
			return respData;
		}
		
   
	}
	
	@At
	@Ok("httl:page.page_main")
	@Fail("redirect:/index.html")
	@RequiresAuthentication
	public void main() {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			throw new RuntimeException("用户未登录!");
		}
	}

	@At
	@RequiresAuthentication
	public void logout(HttpServletResponse response) throws IOException {
		SecurityUtils.getSubject().logout();
		// TODO
		// 用nutz重定向视图总是报异常org.apache.shiro.session.UnknownSessionException: There is no session with id...
		// 所以暂时用这种方式重定向
		response.sendRedirect("index.html");
	}

	@At
	@RequiresAuthentication
	public ResponseData getCurrentUserName() {
		User cUser = (User) SecurityUtils.getSubject().getSession().getAttribute("CurrentUser");
		return systemService.getCurrentUserName(cUser);
	}

	
}