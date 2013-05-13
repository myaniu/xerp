package com.nutzside.common.web.ajax;

import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;

public class Ajax {

	public static AjaxReturn ok() {

		return ok_navtab(null);
	}

	public static AjaxReturn fail() {

		return fail_navtab(null);
	}

	public static AjaxReturn expired() {

		return expired_navtab(null);
	}

	public static AjaxReturn ok_navtab(String navTabId) {
		AjaxReturn re = new AjaxReturn();
		re.statusCode = 200;
		if (!Strings.isEmpty(navTabId)) {
			re.setNavTabId(navTabId);
			re.setCallbackType("closeCurrent");
		}
		return re;
	}

	public static AjaxReturn fail_navtab(String navTabId) {
		AjaxReturn re = new AjaxReturn();
		re.statusCode = 300;
		if (!Strings.isEmpty(navTabId)) {
			re.setNavTabId(navTabId);
			re.setCallbackType("closeCurrent");
		}
		return re;
	}

	public static AjaxReturn expired_navtab(String navTabId) {
		AjaxReturn re = new AjaxReturn();
		re.statusCode = 300;
		re.message = "ajax.expired";
		if (!Strings.isEmpty(navTabId)) {
			re.setNavTabId(navTabId);
			re.setCallbackType("closeCurrent");
		}
		return re;
	}

	/**
	 * @return 获得一个map，用来存放返回的结果。
	 */
	public static NutMap one() {
		return new NutMap();
	}
}
