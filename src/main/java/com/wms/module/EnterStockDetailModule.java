package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.EnterStockDetail;
import com.wms.service.EnterStockDetailService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/enterStockDetail")
public class EnterStockDetailModule {
	
	@Inject
	private EnterStockDetailService enterStockDetailService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") EnterStockDetail enterStockDetailSearch) {
		return enterStockDetailService.getGridData(jqReq, isSearch, enterStockDetailSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") EnterStockDetail enterStockDetail) {
		return enterStockDetailService.CUDEnterStockDetail(oper, ids, enterStockDetail);
	}
	
	@At
	@Ok("httl:wms.enterstockdetail_manager")
	@RequiresPermissions("httl:read:enterstockdetail_manager")
	public void enterstockdetail_manager() {
	}
	
}