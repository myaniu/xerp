package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.EnterStock;
import com.wms.service.EnterStockService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/enterStock")
public class EnterStockModule {
	
	@Inject
	private EnterStockService enterStockService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") EnterStock enterStockSearch) {
		return enterStockService.getGridData(jqReq, isSearch, enterStockSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") EnterStock enterStock) {
		return enterStockService.CUDEnterStock(oper, ids, enterStock);
	}
	
	@At
	@Ok("httl:wms.enterstock_manager")
	@RequiresPermissions("httl:read:enterstock_manager")
	public void enterstock_manager() {
	}
	
}