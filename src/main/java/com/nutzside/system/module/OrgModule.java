package com.nutzside.system.module;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.nutzside.system.domain.Organization;
import com.nutzside.system.service.OrganizationService;

@IocBean
@At("/system/org")
public class OrgModule {
	
	@Inject
	private OrganizationService organizationService;
	@At
	@Ok("httl:system.org_list_oneLookup")
	public Map<String, Object> list(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage, @Param("..") Organization obj) {

		return organizationService.Pagerlist(pageNum, numPerPage, obj);
	}

}
