package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.StoreHouse;
import com.wms.service.StoreHouseService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/storeHouse")
public class StoreHouseModule {
	
	@Inject
	private StoreHouseService storeHouseService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") StoreHouse storeHouseSearch) {
		return storeHouseService.getGridData(jqReq, isSearch, storeHouseSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") StoreHouse storeHouse) {
		return storeHouseService.CUDStoreHouse(oper, ids, storeHouse);
	}
	
	@At
	@Ok("httl:wms.storehouse_manager")
	@RequiresPermissions("httl:read:storehouse_manager")
	public void storehouse_manager() {
	}
	
}