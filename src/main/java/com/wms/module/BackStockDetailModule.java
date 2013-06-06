package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.BackStockDetail;
import com.wms.service.BackStockDetailService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/backStockDetail")
public class BackStockDetailModule {
	
	@Inject
	private BackStockDetailService backStockDetailService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") BackStockDetail backStockDetailSearch) {
		return backStockDetailService.getGridData(jqReq, isSearch, backStockDetailSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") BackStockDetail backStockDetail) {
		return backStockDetailService.CUDBackStockDetail(oper, ids, backStockDetail);
	}
	
	@At
	@Ok("httl:wms.backstockdetail_manager")
	@RequiresPermissions("httl:read:backstockdetail_manager")
	public void backstockdetail_manager() {
	}
	
}