/**
 * 
 */
package com.nutzside.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ajax 工具类 <br>
 * 
 * @author Dawn email: csg0328#gmail.com
 * @date 2011-11-22 上午11:33:54
 * @version 1.0
 * @since 1.0
 */
public class AjaxUtil {

	public static final int OK = 200;
	public static final int FAIL = 300;
	
	public static Map<String, Object> dialogAjax(int statusCode
			) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", statusCode);
		map.put("message", (statusCode == OK) ? "操作成功！" : "操作失败！");
		return map;
	}
	
	public static Map<String, Object> dialogAjaxMsg(int statusCode,String Msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusCode", statusCode);
		map.put("message", Msg);
	
		return map;
	}

	/**
	 * Ajax服务器端响应
	 * 
	 * @param statusCode
	 *            状态码
	 * @return
	 */
	public static Map<String, Object> dialogAjaxDone(int statusCode) {
		return dialogAjaxDone(statusCode);
	}

}
