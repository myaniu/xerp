package com.nutzside.system.module;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.system.domain.Organization;
import com.nutzside.system.service.OrganizationService;

@IocBean
@At("/system/organization")
public class OrganizationModule {

	@Inject
	private OrganizationService organizationService;

	@At
	@RequiresPermissions("organization:read:*")
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Organization organizationSearch) {
		return organizationService.getGridData(jqReq, isSearch, organizationSearch);
	}

	@At
	@RequiresPermissions("organization:create, delete, update:*")
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids,
			@Param("..") Organization organization) {
		return organizationService.CUDOrganization(oper, ids, organization);
	}

	@At
	@RequiresPermissions("organization:read:*")
	public ResponseData getNodes(@Param("id") Long id) {
		return organizationService.getNodes(id);
	}
	
	@At
	@Ok("httl:system.organization_manage")
	@RequiresPermissions("httl:read:organization_manage")
	public void organization_manage() {
	}
	
	
	@At
	@Ok("httl:system.post_manage")
	@RequiresPermissions("httl:read:post_manage")
	public void post_manage() {
	}
}