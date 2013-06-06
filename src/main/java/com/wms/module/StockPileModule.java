package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.StockPile;
import com.wms.service.StockPileService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/stockPile")
public class StockPileModule {
	
	@Inject
	private StockPileService stockPileService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") StockPile stockPileSearch) {
		return stockPileService.getGridData(jqReq, isSearch, stockPileSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") StockPile stockPile) {
		return stockPileService.CUDStockPile(oper, ids, stockPile);
	}
	
	@At
	@Ok("httl:wms.stockpile_manager")
	@RequiresPermissions("httl:read:stockpile_manager")
	public void stockpile_manager() {
	}
	
}