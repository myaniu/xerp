package com.nutzside.common.web.ajax;

import org.nutz.lang.util.NutMap;

public class Ajax {

	public static AjaxReturn ok() {

		return ok_navtab();
	}

	public static AjaxReturn fail() {

		return fail_navtab();
	}

	public static AjaxReturn ok_navtab() {
		AjaxReturn re = new AjaxReturn();
		re.statusCode = 200;
		return re;
	}

	public static AjaxReturn fail_navtab() {
		AjaxReturn re = new AjaxReturn();
		re.statusCode = 300;
		return re;
	}


	/**
	 * @return 获得一个map，用来存放返回的结果。
	 */
	public static NutMap one() {
		return new NutMap();
	}
}
