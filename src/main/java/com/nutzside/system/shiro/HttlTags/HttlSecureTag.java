package com.nutzside.system.shiro.HttlTags;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class HttlSecureTag {
	
	    public HttlSecureTag() {
	    }

	    protected Subject getSubject() {
	        return SecurityUtils.getSubject();
	    }

}
