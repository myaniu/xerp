package com.nutzside.system.module;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.system.domain.SysPara;
import com.nutzside.system.service.SysParaService;

@IocBean
@At("/system/sysPara")
public class SysParaModule {

	@Inject
	private SysParaService sysParaService;

	@At
	@RequiresPermissions("syspara:read:*")
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") SysPara sysParaSearch) {
		return sysParaService.getGridData(jqReq, isSearch, sysParaSearch);
	}

	@At
	@RequiresPermissions("syspara:create, delete, update:*")
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") SysPara sysPara) {
		return sysParaService.CUDSysPara(oper, ids, sysPara);
	}
	@At
	@Ok("httl:system.syspara_manage")
	@RequiresPermissions("httl:read:syspara_manage")
	public void syspara_manage() {
	}
}