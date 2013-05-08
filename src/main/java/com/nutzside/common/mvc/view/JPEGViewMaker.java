package com.nutzside.common.mvc.view;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

/**
 * @author idor(sjbwylbs@gmail.com)
 */
public class JPEGViewMaker implements ViewMaker {

	@Override
	public View make(Ioc ioc, String type, String value) {
		//log.debugf("Type:%s,Value:%s", type, value);
		if ("jpg".equalsIgnoreCase(type)) {
			return new JPEGView("image/jpeg");
		}
		return null;
	}

}
