package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.Customer;
import com.wms.service.CustomerService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/customer")
public class CustomerModule {
	
	@Inject
	private CustomerService customerService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") Customer customerSearch) {
		return customerService.getGridData(jqReq, isSearch, customerSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Customer customer) {
		return customerService.CUDCustomer(oper, ids, customer);
	}
	
	@At
	@Ok("httl:wms.customer_manager")
	@RequiresPermissions("httl:read:customer_manager")
	public void customer_manager() {
	}
	
}