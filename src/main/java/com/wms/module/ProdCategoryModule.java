package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.ProdCategory;
import com.wms.service.ProdCategoryService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/prodCategory")
public class ProdCategoryModule {
	
	@Inject
	private ProdCategoryService prodCategoryService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") ProdCategory prodCategorySearch) {
		return prodCategoryService.getGridData(jqReq, isSearch, prodCategorySearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") ProdCategory prodCategory) {
		return prodCategoryService.CUDProdCategory(oper, ids, prodCategory);
	}
	
	@At
	@Ok("httl:wms.prodcategory_manager")
	@RequiresPermissions("httl:read:prodcategory_manager")
	public void prodcategory_manager() {
	}
	
}