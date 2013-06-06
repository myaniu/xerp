package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.Product;
import com.wms.service.ProductService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/product")
public class ProductModule {
	
	@Inject
	private ProductService productService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") Product productSearch) {
		return productService.getGridData(jqReq, isSearch, productSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Product product) {
		return productService.CUDProduct(oper, ids, product);
	}
	
	@At
	@Ok("httl:wms.product_manager")
	@RequiresPermissions("httl:read:product_manager")
	public void product_manager() {
	}
	
}