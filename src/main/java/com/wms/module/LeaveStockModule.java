package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.LeaveStock;
import com.wms.service.LeaveStockService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/leaveStock")
public class LeaveStockModule {
	
	@Inject
	private LeaveStockService leaveStockService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") LeaveStock leaveStockSearch) {
		return leaveStockService.getGridData(jqReq, isSearch, leaveStockSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") LeaveStock leaveStock) {
		return leaveStockService.CUDLeaveStock(oper, ids, leaveStock);
	}
	
	@At
	@Ok("httl:wms.leavestock_manager")
	@RequiresPermissions("httl:read:leavestock_manager")
	public void leavestock_manager() {
	}
	
}