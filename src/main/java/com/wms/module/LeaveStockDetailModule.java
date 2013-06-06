package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.LeaveStockDetail;
import com.wms.service.LeaveStockDetailService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/leaveStockDetail")
public class LeaveStockDetailModule {
	
	@Inject
	private LeaveStockDetailService leaveStockDetailService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") LeaveStockDetail leaveStockDetailSearch) {
		return leaveStockDetailService.getGridData(jqReq, isSearch, leaveStockDetailSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") LeaveStockDetail leaveStockDetail) {
		return leaveStockDetailService.CUDLeaveStockDetail(oper, ids, leaveStockDetail);
	}
	
	@At
	@Ok("httl:wms.leavestockdetail_manager")
	@RequiresPermissions("httl:read:leavestockdetail_manager")
	public void leavestockdetail_manager() {
	}
	
}