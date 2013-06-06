package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.BuyOrderDetail;
import com.wms.service.BuyOrderDetailService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/buyOrderDetail")
public class BuyOrderDetailModule {
	
	@Inject
	private BuyOrderDetailService buyOrderDetailService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") BuyOrderDetail buyOrderDetailSearch) {
		return buyOrderDetailService.getGridData(jqReq, isSearch, buyOrderDetailSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") BuyOrderDetail buyOrderDetail) {
		return buyOrderDetailService.CUDBuyOrderDetail(oper, ids, buyOrderDetail);
	}
	
	@At
	@Ok("httl:wms.buyorderdetail_manager")
	@RequiresPermissions("httl:read:buyorderdetail_manager")
	public void buyorderdetail_manager() {
	}
	
}