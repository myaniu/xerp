package com.nutzside.qrtz.module;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.qrtz.domain.SimpleTriggers;
import com.nutzside.qrtz.service.SimpleTriggersService;

@IocBean
@At("/qrtz/simpleTriggers")
public class SimpleTriggersModule {
	
	@Inject
	private SimpleTriggersService simpleTriggersService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") SimpleTriggers simpleTriggersSearch) {
		return simpleTriggersService.getGridData(jqReq, isSearch, simpleTriggersSearch);
	}
	
	@At
	@Ok("httl:qrtz.simpletriggers_manager")
	@RequiresPermissions("httl:read:simpletriggers_manager")
	public void simpletriggers_manager() {
	}
}