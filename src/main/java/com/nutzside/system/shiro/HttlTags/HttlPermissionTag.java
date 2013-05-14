package com.nutzside.system.shiro.HttlTags;

public class HttlPermissionTag extends HttlSecureTag {

	protected boolean isPermitted(String p) {
		return getSubject() != null && getSubject().isPermitted(p);
	}
}
