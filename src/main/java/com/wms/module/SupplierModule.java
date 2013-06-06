package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.Supplier;
import com.wms.service.SupplierService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/supplier")
public class SupplierModule {
	
	@Inject
	private SupplierService supplierService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") Supplier supplierSearch) {
		return supplierService.getGridData(jqReq, isSearch, supplierSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Supplier supplier) {
		return supplierService.CUDSupplier(oper, ids, supplier);
	}
	
	@At
	@Ok("httl:wms.supplier_manager")
	@RequiresPermissions("httl:read:supplier_manager")
	public void supplier_manager() {
	}
	
}