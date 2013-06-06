package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.SaleOrder;
import com.wms.service.SaleOrderService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/saleOrder")
public class SaleOrderModule {
	
	@Inject
	private SaleOrderService saleOrderService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") SaleOrder saleOrderSearch) {
		return saleOrderService.getGridData(jqReq, isSearch, saleOrderSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") SaleOrder saleOrder) {
		return saleOrderService.CUDSaleOrder(oper, ids, saleOrder);
	}
	
	@At
	@Ok("httl:wms.saleorder_manager")
	@RequiresPermissions("httl:read:saleorder_manager")
	public void saleorder_manager() {
	}
	
}