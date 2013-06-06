package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.Proceeds;
import com.wms.service.ProceedsService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/proceeds")
public class ProceedsModule {
	
	@Inject
	private ProceedsService proceedsService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") Proceeds proceedsSearch) {
		return proceedsService.getGridData(jqReq, isSearch, proceedsSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Proceeds proceeds) {
		return proceedsService.CUDProceeds(oper, ids, proceeds);
	}
	
	@At
	@Ok("httl:wms.proceeds_manager")
	@RequiresPermissions("httl:read:proceeds_manager")
	public void proceeds_manager() {
	}
	
}