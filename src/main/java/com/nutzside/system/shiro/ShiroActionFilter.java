package com.nutzside.system.shiro;

import java.lang.reflect.Method;

import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.lang.Lang;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.DefaultViewMaker;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.mvc.view.UTF8JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nutzside.common.domain.AjaxResData;

/**
 * 在入口方法中应用Shiro注解来进行安全过滤
 * 
 * @author wendal
 * 
 */
public class ShiroActionFilter implements ActionFilter {
	private static Logger logger = LoggerFactory
			.getLogger(ShiroActionFilter.class);

	@Override
	public View match(final ActionContext actionContext) {
		try {
			ShiroAnnotationsAuthorizingMethodInterceptor.defaultAuth
					.assertAuthorized(new MethodInvocation() {

						@Override
						public Object proceed() throws Throwable {
							throw Lang.noImplement();
						}

						@Override
						public Object getThis() {
							return actionContext.getModule();
						}

						@Override
						public Method getMethod() {
							return actionContext.getMethod();
						}

						@Override
						public Object[] getArguments() {
							return actionContext.getMethodArgs();
						}
					});

		} catch (UnauthorizedException e) {
			String permission = actionContext.getMethod()
					.getAnnotation(RequiresPermissions.class).value()[0];
			logger.warn("权限不足", e);
			UTF8JsonView jsonView = new UTF8JsonView(null);
			jsonView.setData(AjaxResData.getInstanceErrorNotice("当前用户无该权限"
					+ permission));
			return jsonView;
		} catch (AuthorizationException e) {
			return whenAuthFail(actionContext, e);
		}
		return null;
	}

	private View view;

	public ShiroActionFilter() {
		view = new ServerRedirectView("/index.jsp");
	}

	public ShiroActionFilter(String view) {
		if (view.contains(":")) {
			String[] vs = view.split(":", 2);
			this.view = new DefaultViewMaker().make(null, vs[0], vs[1]);
		} else {
			this.view = new ServerRedirectView(view);
		}
	}

	protected View whenAuthFail(ActionContext ctx, AuthorizationException e) {
		return view;
	}
}
