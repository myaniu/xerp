package com.nutzside.common.mvc.view;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

public class ReportViewMaker implements ViewMaker {

	public static final String VIEW_JASPER = "jasper";
	public static final String VIEW_JASPER2 = "jasper2";

	@Override
	public View make(Ioc ioc, String type, String value) {
		
		if (VIEW_JASPER.equals(type))
			return new HtmlReportView();
		if (VIEW_JASPER2.equals(type))
			return new HtmlReport2View(value);
		return null;
	}

}
