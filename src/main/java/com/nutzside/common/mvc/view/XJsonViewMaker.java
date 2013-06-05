package com.nutzside.common.mvc.view;

import org.nutz.ioc.Ioc;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

/**
 * @author Administrator 所用的自定义视图ViewMaker
 */
public class XJsonViewMaker implements ViewMaker {

	public static final String VIEW_JSON = "Xjson";
	public static final String VIEW_JASPER = "jasper";
	public static final String VIEW_JASPER2 = "jasper2";

	@Override
	public View make(Ioc ioc, String type, String value) {
		if (VIEW_JSON.equals(type))
			if (Strings.isBlank(value))
				return new XJsonView(JsonFormat.compact());
			else
				return new XJsonView(Json.fromJson(JsonFormat.class, value));
		if (VIEW_JASPER.equals(type))
			return new HtmlReportView();
		if (VIEW_JASPER2.equals(type))
			return new HtmlReport2View(value);
		return null;
	}
}