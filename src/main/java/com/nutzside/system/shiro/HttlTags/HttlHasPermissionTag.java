package com.nutzside.system.shiro.HttlTags;

public class HttlHasPermissionTag extends HttlPermissionTag{
	 public HttlHasPermissionTag() {
	    }

	    protected boolean showTagBody(String p) {
	        return isPermitted(p);
	    }
}
