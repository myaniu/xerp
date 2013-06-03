package com.nutzside.system.module;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.system.domain.Client;
import com.nutzside.system.service.ClientService;

@IocBean
@At("/system/client")
public class ClientModule {

	@Inject
	private ClientService clientService;

	@At
	@RequiresPermissions("client:read:*")
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Client clientSearch, @Param("userName") String userName) {
		return clientService.getGridData(jqReq, isSearch, clientSearch, userName);
	}

	@At
	@RequiresPermissions("client:kickoff:*")
	public ResponseData kickOff(@Param("sessionIds[]") String[] sessionIds) {
		return clientService.kickOff(sessionIds);
	}
}