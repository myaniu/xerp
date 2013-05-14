package com.nutzside.system.shiro.HttlTags;

public class HttlPermissionTag extends HttlSecureTag {
	private String name = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected boolean isPermitted(String p) {
		return getSubject() != null && getSubject().isPermitted(p);
	}
}
