package com.nutzside.system.shiro.HttlKit;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class HttlSecureKit {
	
	    public HttlSecureKit() {
	    }

	    protected Subject getSubject() {
	        return SecurityUtils.getSubject();
	    }

}
