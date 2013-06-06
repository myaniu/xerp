package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.SaleOrderDetail;
import com.wms.service.SaleOrderDetailService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/saleOrderDetail")
public class SaleOrderDetailModule {
	
	@Inject
	private SaleOrderDetailService saleOrderDetailService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") SaleOrderDetail saleOrderDetailSearch) {
		return saleOrderDetailService.getGridData(jqReq, isSearch, saleOrderDetailSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") SaleOrderDetail saleOrderDetail) {
		return saleOrderDetailService.CUDSaleOrderDetail(oper, ids, saleOrderDetail);
	}
	
	@At
	@Ok("httl:wms.saleorderdetail_manager")
	@RequiresPermissions("httl:read:saleorderdetail_manager")
	public void saleorderdetail_manager() {
	}
	
}