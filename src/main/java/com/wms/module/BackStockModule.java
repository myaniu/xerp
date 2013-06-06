package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.BackStock;
import com.wms.service.BackStockService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/backStock")
public class BackStockModule {
	
	@Inject
	private BackStockService backStockService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") BackStock backStockSearch) {
		return backStockService.getGridData(jqReq, isSearch, backStockSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") BackStock backStock) {
		return backStockService.CUDBackStock(oper, ids, backStock);
	}
	
	@At
	@Ok("httl:wms.backstock_manager")
	@RequiresPermissions("httl:read:backstock_manager")
	public void backstock_manager() {
	}
	
}