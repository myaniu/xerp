package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.BuyOrder;
import com.wms.service.BuyOrderService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/buyOrder")
public class BuyOrderModule {
	
	@Inject
	private BuyOrderService buyOrderService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") BuyOrder buyOrderSearch) {
		return buyOrderService.getGridData(jqReq, isSearch, buyOrderSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") BuyOrder buyOrder) {
		return buyOrderService.CUDBuyOrder(oper, ids, buyOrder);
	}
	
	@At
	@Ok("httl:wms.buyorder_manager")
	@RequiresPermissions("httl:read:buyorder_manager")
	public void buyorder_manager() {
	}
	
}