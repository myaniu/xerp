package com.nutzside.system.shiro.HttlKit;

public class HttlPermissionKit extends HttlSecureKit {

	protected boolean isPermitted(String p) {
		return getSubject() != null && getSubject().isPermitted(p);
	}
}
